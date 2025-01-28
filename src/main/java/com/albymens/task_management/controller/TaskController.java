package com.albymens.task_management.controller;

import com.albymens.task_management.TaskService;
import com.albymens.task_management.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TaskController {
    @Autowired
    TaskService taskService;

    @GetMapping("/add")
    public ResponseEntity<Task> createTask(@RequestBody Task task,
                                           @RequestParam(value = "username") String username){
        return ResponseEntity.status(202).body(taskService.createTask(username,task));
    }
}
