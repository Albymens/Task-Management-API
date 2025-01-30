package com.albymens.task_management.service;

import com.albymens.task_management.entity.Priority;
import com.albymens.task_management.entity.Status;
import com.albymens.task_management.entity.Task;
import com.albymens.task_management.entity.User;
import com.albymens.task_management.repository.TaskRepository;
import com.albymens.task_management.repository.UserRepository;
import com.albymens.task_management.response.APIResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TaskServiceTest {
    @InjectMocks
    TaskService taskService;
    @Mock
    TaskRepository taskRepository;
    @Mock
    UserRepository userRepository;

    Task task;
    User user;
    APIResponse apiResponse;
    LocalDate fixedDate;

    @BeforeEach()
    public void setup(){
        MockitoAnnotations.openMocks(this);

        task = new Task();
        task.setTitle("Learn");
        task.setDescription("Deep dive into Deepseek");
        task.setPriority(Priority.HIGH);
        task.setStatus(Status.COMPLETED);
        fixedDate = LocalDate.of(2025, 2, 5);
        task.setDeadline(fixedDate);
        task.setId(1L);

        user = new User();
        user.setUsername("Alby");
        user.setPassword("30AQ23ER");
        task.setUser(user);

    }

    @Test
    public void createTask() {
        when(userRepository.findByUsername(Mockito.anyString())).thenReturn(user);
        String responseMsg = "Task created successfully";
        ResponseEntity<APIResponse> response = taskService.createTask("Sonia", task);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(responseMsg, Objects.requireNonNull(response.getBody()).getMessage());
    }

    @Test
    public void retrieveUserTasks() {
        when(userRepository.findByUsername(Mockito.anyString())).thenReturn(user);
        String responseMsg = "Tasks retrieved successfully";
        ResponseEntity<APIResponse> response = taskService.retrieveUserTasks("Sonia");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseMsg, Objects.requireNonNull(response.getBody()).getMessage());
    }

    @Test
    public void updateUserTask() {
        Task updatedTask = new Task();
        updatedTask.setUser(user);
        updatedTask.setDescription("To update task description");
        when(userRepository.findByUsername(Mockito.anyString())).thenReturn(user);
        when(taskRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(task));
        String responseMsg = "Task updated successfully!!";
        ResponseEntity<APIResponse> response = taskService.updateUserTask(user.getUsername(),updatedTask, task.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseMsg, Objects.requireNonNull(response.getBody()).getMessage());
    }

    @Test
    public void deleteTask() {
        when(userRepository.findByUsername(Mockito.anyString())).thenReturn(user);
        when(taskRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(task));
        String responseMsg = "Task deleted successfully";
        ResponseEntity<APIResponse> response = taskService.deleteTask("Sonia", task.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseMsg, Objects.requireNonNull(response.getBody()).getMessage());
    }

    @Test
    public void filterTasks() {
        when(userRepository.findByUsername(Mockito.anyString())).thenReturn(user);
        when(taskRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(task));
        ResponseEntity<APIResponse> response = taskService.filterTasks("Sonia",
                Priority.HIGH, Status.COMPLETED, fixedDate);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}