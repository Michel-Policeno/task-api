package com.br.todoapi.todo_api.service;

import com.br.todoapi.todo_api.entity.User;
import com.br.todoapi.todo_api.repository.UserRepository;
import com.br.todoapi.todo_api.security.JWTUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JWTUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public String login (String email, String senha){
    var user = userRepository.findByEmail(email)
        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

    if (!passwordEncoder.matches(senha, user.getSenha())){
        throw new RuntimeException("Senha inválida");
    }

    return jwtUtil.generateToken(email);
    }


    public User register(String email, String senha){
        if(userRepository.existsByEmail(email)){
            throw new RuntimeException("Usuário já cadastrado");
        }
        String password = passwordEncoder.encode(senha);
        User newUser = new User(email, password);
        return userRepository.save(newUser);
    }





}