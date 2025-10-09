package com.br.todoapi.todo_api.repository;

import com.br.todoapi.todo_api.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
