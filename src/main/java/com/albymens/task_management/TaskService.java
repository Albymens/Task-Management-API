package com.albymens.task_management;

import com.albymens.task_management.entity.Task;
import com.albymens.task_management.entity.User;
import com.albymens.task_management.repository.TaskRepository;
import com.albymens.task_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    @Autowired
    TaskRepository taskRepository;
    UserRepository userRepository;

    public Task createTask(String username, Task task){
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new RuntimeException("User not Found"));
        task.setUser(user);
        return taskRepository.save(task);
    }

}
