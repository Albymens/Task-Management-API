package com.albymens.task_management.repository;

import com.albymens.task_management.entity.Priority;
import com.albymens.task_management.entity.Status;
import com.albymens.task_management.entity.Task;
import com.albymens.task_management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUser(User user);
    List<Task> findByStatusAndUsername(Status status, String username);
    List<Task> findByUsernameANDPriority(String username, Priority priority);
    List<Task> findByUsernameANDDeadline(String username, LocalDate deadline);
}
