package com.albymens.task_management.controller;

import com.albymens.task_management.entity.Priority;
import com.albymens.task_management.entity.Status;
import com.albymens.task_management.response.APIResponse;
import com.albymens.task_management.service.TaskService;
import com.albymens.task_management.entity.Task;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
public class TaskController {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    TaskService taskService;

    @PostMapping("/tasks")
    public ResponseEntity<APIResponse> createTask(@RequestBody @Valid Task task,
                                                  @RequestHeader(value = "username") String username,
                                                  BindingResult result){
        if(result.hasErrors()){
            Map<String, String> errorMessage = new HashMap<>();
            result.getAllErrors().forEach(error -> {
                String fieldName = ((FieldError) error).getField();
                errorMessage.put(fieldName, error.getDefaultMessage());
                logger.error("Error creating user details {} {}", fieldName, error.getDefaultMessage());
            });
            return ResponseEntity.status(400).body(new APIResponse(false, null, errorMessage));
        }
        return taskService.createTask(username,task);
    }

    @GetMapping("/tasks")
    public ResponseEntity<APIResponse> retrieveUserTasks(@RequestHeader(name = "username") String username){
        return taskService.retrieveUserTasks(username);
    }

    @PutMapping("/tasks/{taskId}")
    public ResponseEntity<APIResponse> updateTask(@RequestHeader("username") String username,
                                                  @PathVariable Long taskId,
                                                  @RequestBody Task updatedTask){
        return taskService.updateUserTask(username, updatedTask, taskId);
    }

    @DeleteMapping("/tasks/{taskId}")
    public ResponseEntity<APIResponse> deleteTak(@RequestHeader("username") String username,
                                                 @PathVariable Long taskId){
        return taskService.deleteTask(username, taskId);
    }

    @GetMapping("/tasks/filter")
    public ResponseEntity<APIResponse> filterTask(@RequestHeader("username") String username,
                                                  @RequestParam(required = false) Status status,
                                                  @RequestParam(required = false) Priority priority,
                                                  @RequestParam(required = false) LocalDate deadline){
        return taskService.filterTasks(username, priority, status, deadline);
    }

}
