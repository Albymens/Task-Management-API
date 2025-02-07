package com.albymens.task_management.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "task")
public class Task implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Schema(hidden = true)
    private Long id;

    @NotEmpty
    @Schema(description = "Title of the task")
    private String title;

    @Schema(description = "A detailed description of the task or anything you will like to highlight")
    private String description;

    @Enumerated(EnumType.STRING)
    @Schema(description = "The level of task priority (HIGH, MEDIUM, LOW)")
    private Priority priority;

    @Enumerated(EnumType.STRING)
    @Schema(description = "The status of the task (BACKLOG, TODO, PENDING, IN_PROGRESS, COMPLETED")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @Schema(description = "The owner of the task")
    private User user;

    private LocalDate deadline;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }
}
