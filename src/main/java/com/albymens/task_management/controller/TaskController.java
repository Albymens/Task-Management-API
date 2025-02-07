package com.albymens.task_management.controller;

import com.albymens.task_management.entity.Priority;
import com.albymens.task_management.entity.Status;
import com.albymens.task_management.response.APIResponse;
import com.albymens.task_management.service.TaskService;
import com.albymens.task_management.entity.Task;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Task management", description = "Endpoints for task management, It allows users to create their own task," +
        " edit an existing tasks, delete a task and filter task based on priority, status and deadline")
public class TaskController {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    TaskService taskService;

    @Operation(
            summary = "Create Task",
            description = "This API allows users to create their own task, set the priority and status of the task"
    )
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
    @Operation(
            summary = "Retrieve all tasks",
            description = "This API allows users to retrieve all their task"
    )
    public ResponseEntity<APIResponse> retrieveUserTasks(@RequestHeader(name = "username") String username){
        return taskService.retrieveUserTasks(username);
    }

    @PutMapping("/tasks/{taskId}")
    @Operation(
            summary = "Update a task",
            description = "This API allows users to modify or edit a specific task"
    )
    public ResponseEntity<APIResponse> updateTask(@RequestHeader("username") String username,
                                                  @PathVariable Long taskId,
                                                  @RequestBody Task updatedTask){
        return taskService.updateUserTask(username, updatedTask, taskId);
    }

    @DeleteMapping("/tasks/{taskId}")
    @Operation(
            summary = "Delete task",
            description = "This API allows users to delete a specific task"
    )
    public ResponseEntity<APIResponse> deleteTak(@RequestHeader("username") String username,
                                                 @PathVariable Long taskId){
        return taskService.deleteTask(username, taskId);
    }

    @GetMapping("/tasks/filter")
    @Operation(
            summary = "Filter task based on it priority, status and deadline",
            description = "This API allows users to filter and retrieve task based on it priority, status and deadline"
    )
    public ResponseEntity<APIResponse> filterTask(@RequestHeader("username") String username,
                                                  @RequestParam(required = false) Status status,
                                                  @RequestParam(required = false) Priority priority,
                                                  @RequestParam(required = false) LocalDate deadline){
        return taskService.filterTasks(username, priority, status, deadline);
    }

}
