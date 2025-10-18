package com.br.todoapi.todo_api.repository;

import com.br.todoapi.todo_api.entity.Task;
import com.br.todoapi.todo_api.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUserAndAtivoTrue(User user, Sort sort);
    Optional<Task> findByIdAndUser(Long id, User user);

}
