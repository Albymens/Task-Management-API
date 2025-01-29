package com.albymens.task_management.service;

import ch.qos.logback.core.util.StringUtil;
import com.albymens.task_management.entity.Task;
import com.albymens.task_management.entity.User;
import com.albymens.task_management.repository.TaskRepository;
import com.albymens.task_management.repository.UserRepository;
import com.albymens.task_management.response.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    TaskRepository taskRepository;
    @Autowired
    UserRepository userRepository;

    public ResponseEntity<APIResponse> createTask(String username, Task task){
        User user = userRepository.findByUsername(username);
        if (null == user){
            return ResponseEntity.badRequest().body(
                    new APIResponse(false, "User not found", null)
            );
        }
        task.setUser(user);
        return ResponseEntity.status(202).body(
                new APIResponse(true, "Task created successfully", taskRepository.save(task))
        );
    }

    public ResponseEntity<APIResponse> retrieveUserTasks(String username){
        User user = userRepository.findByUsername(username);
        if (null == user){
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
            return ResponseEntity.status(404).body(
                    new APIResponse(false, "Task not found", null)
            );
        }

        User user = userRepository.findByUsername(username);
        if(user == null){
            return ResponseEntity.status(404).body(
                    new APIResponse(false, "Unable to retrieve user details, user not found", null)
            );
        }

        if(!task.get().getUser().equals(user)){
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
            return ResponseEntity.status(404).body(
                    new APIResponse(false, "User not found", null)
            );
        }

        Optional<Task> task = taskRepository.findById(taskId);
        if(task.isEmpty()){
            return ResponseEntity.status(404).body(
                    new APIResponse(false, "Task not found", null)
            );
        }

        if(!user.equals(task.get().getUser())){
            return ResponseEntity.status(401).body(
                    new APIResponse(false, "unauthorized", null)
            );
        }

        taskRepository.delete(task.get());
        return ResponseEntity.ok(
               new APIResponse(true, "Task deleted successfully", null)
        );

    }
}
