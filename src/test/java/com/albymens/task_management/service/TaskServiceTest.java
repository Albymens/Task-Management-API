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

import java.util.Objects;

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

    @BeforeEach()
    public void setup(){
        MockitoAnnotations.openMocks(this);

        task = new Task();
        task.setTitle("Learn");
        task.setDescription("Deep dive into Deepseek");
        task.setPriority(Priority.MEDIUM);
        task.setStatus(Status.PENDING);

        user = new User();
        user.setUsername("Alby");
        user.setPassword("30AQ23ER");

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
    }

    @Test
    public void deleteTask() {
    }

    @Test
    public void filterTasks() {
    }
}