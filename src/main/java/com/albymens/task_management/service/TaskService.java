package com.albymens.task_management.service;

import ch.qos.logback.core.util.StringUtil;
import com.albymens.task_management.entity.Priority;
import com.albymens.task_management.entity.Status;
import com.albymens.task_management.entity.Task;
import com.albymens.task_management.entity.User;
import com.albymens.task_management.repository.TaskRepository;
import com.albymens.task_management.repository.UserRepository;
import com.albymens.task_management.response.APIResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    TaskRepository taskRepository;
    @Autowired
    UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);

    public ResponseEntity<APIResponse> createTask(String username, Task task){
        User user = userRepository.findByUsername(username);
        if (null == user){
            logger.error("Unable to create user {}", username);
            return ResponseEntity.badRequest().body(
                    new APIResponse(false, "User not found", null)
            );
        }
        task.setUser(user);
        return ResponseEntity.status(201).body(
                new APIResponse(true, "Task created successfully", taskRepository.save(task))
        );
    }

    public ResponseEntity<APIResponse> retrieveUserTasks(String username){
        User user = userRepository.findByUsername(username);
        if (null == user){
            logger.error("Unable to retrieve {} tasks, user not found", username);
            return ResponseEntity.status(404).body(
                    new APIResponse(false, "User not found", null)
            );
        }
        return ResponseEntity.ok(
                new APIResponse(true, "Tasks retrieved successfully", taskRepository.findByUser(user))
        );
    }

    public ResponseEntity<APIResponse> updateUserTask(String username, Task updatedTask, Long taskId){
        Optional<Task> task = taskRepository.findById(taskId);
        if(task.isEmpty()){
            logger.error("Unable to update {} task, task not found", taskId);
            return ResponseEntity.status(404).body(
                    new APIResponse(false, "Task not found", null)
            );
        }

        User user = userRepository.findByUsername(username);
        if(user == null){
            logger.error("Unable to update {} task, user not found", username);
            return ResponseEntity.status(404).body(
                    new APIResponse(false, "Unable to retrieve user details, user not found", null)
            );
        }

        if(!task.get().getUser().equals(user)){
            logger.error("Unable to update {} task, unauthorized ", username);
            return ResponseEntity.status(401).body(
                    new APIResponse(false, "Unauthorized", null)
            );
        }

        if(StringUtil.notNullNorEmpty(updatedTask.getTitle()))
            task.get().setTitle(updatedTask.getTitle());
        if(StringUtil.notNullNorEmpty(updatedTask.getDescription()))
            task.get().setDescription(updatedTask.getDescription());
        if(null != updatedTask.getDeadline())
            task.get().setDeadline(updatedTask.getDeadline());
        if(null != updatedTask.getStatus())
            task.get().setStatus(updatedTask.getStatus());
        if(null != updatedTask.getPriority())
            task.get().setPriority(updatedTask.getPriority());

        return ResponseEntity.ok(
                new APIResponse(true, "Task updated successfully!!",
                        taskRepository.save(task.get()))
        );
    }

    public ResponseEntity<APIResponse> deleteTask(String username, Long taskId){
        User user = userRepository.findByUsername(username);
        if(null == user){
            logger.error("Unable to delete {} task, user not found", username);
            return ResponseEntity.status(404).body(
                    new APIResponse(false, "User not found", null)
            );
        }

        Optional<Task> task = taskRepository.findById(taskId);
        if(task.isEmpty()){
            logger.error("Unable to delete {} task, task not found", taskId);
            return ResponseEntity.status(404).body(
                    new APIResponse(false, "Task not found", null)
            );
        }

        if(!user.equals(task.get().getUser())){
            logger.error("Unable to delete {} task, unauthorized ", username);
            return ResponseEntity.status(401).body(
                    new APIResponse(false, "unauthorized", null)
            );
        }

        taskRepository.delete(task.get());
        logger.info("Task deleted by {}", username);
        return ResponseEntity.ok(
               new APIResponse(true, "Task deleted successfully", null)
        );
    }

    public ResponseEntity<APIResponse> filterTasks(String username, Priority priority,
                                  Status status, LocalDate deadline){

        User user = userRepository.findByUsername(username);
        if(null == user){
            return ResponseEntity.status(404).body(
                    new APIResponse(false, "User not found", null)
            );
        }

        List<Task> tasks = taskRepository.findByUser(user);
        if(status != null){
            tasks = tasks.stream().filter(task -> task.getStatus().equals(status))
                    .collect(Collectors.toList());
        }

        if(priority != null){
            tasks = tasks.stream().filter(task -> task.getPriority().equals(priority))
                    .collect(Collectors.toList());
        }

        if(deadline != null){
            tasks = tasks.stream().filter(task -> task.getDeadline().equals(deadline))
                    .collect(Collectors.toList());
        }

        return ResponseEntity.ok().body(new APIResponse(true, null, tasks));
    }
}
