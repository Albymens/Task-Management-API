package com.albymens.task_management.dto;

import com.albymens.task_management.entity.User;

public class UserMapper {
    public static UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setTasks(user.getTasks());
        return dto;
    }
}
