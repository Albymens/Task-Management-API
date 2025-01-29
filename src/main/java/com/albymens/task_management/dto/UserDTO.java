package com.albymens.task_management.dto;

import com.albymens.task_management.entity.Task;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class UserDTO implements Serializable {
        @Serial
        private static final long serialVersionUID = 1L;
        private Long id;

        private String username;

        private List<Task> tasks;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public List<Task> getTasks() {
            return tasks;
        }

        public void setTasks(List<Task> tasks) {
            this.tasks = tasks;
        }

    }
