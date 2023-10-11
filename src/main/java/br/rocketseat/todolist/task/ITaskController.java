package br.rocketseat.todolist.task;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ITaskController extends JpaRepository<TaskModel, UUID> {
    
}
