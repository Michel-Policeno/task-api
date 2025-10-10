package com.br.todoapi.todo_api.repository;

import com.br.todoapi.todo_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
        Optional<User> findByUsuario (String usuario);

        boolean existsByUsuario(String usuario);
}
