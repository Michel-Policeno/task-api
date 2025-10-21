package com.br.todoapi.todo_api.service;

import com.br.todoapi.todo_api.entity.User;
import com.br.todoapi.todo_api.repository.UserRepository;
import com.br.todoapi.todo_api.security.JWTUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JWTUtil jwtUtil;

    @InjectMocks
    private AuthService authService;

    private User user;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        user = new User("teste@exemplo.com", "senhaCriptografada");
    }

    @Test
    void deveEfetuarLoginComSucesso() {
        when(userRepository.findByEmail("teste@exemplo.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("123", "senhaCriptografada")).thenReturn(true);
        when(jwtUtil.generateToken("teste@exemplo.com")).thenReturn("token123");

        String token = authService.login("teste@exemplo.com", "123");

        assertEquals("token123", token);
        verify(jwtUtil).generateToken("teste@exemplo.com");
    }

    @Test
    void deveFalharQuandoSenhaIncorreta() {
        when(userRepository.findByEmail("teste@exemplo.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("errada", "senhaCriptografada")).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                authService.login("teste@exemplo.com", "errada")
        );

        assertEquals("Senha inválida", ex.getMessage());
    }


    @Test
    void deveFalharQuandoUsuarioNaoExiste() {
        when(userRepository.findByEmail("invalido@exemplo.com")).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                authService.login("invalido@exemplo.com", "123")
        );

        assertEquals("Usuário não encontrado", ex.getMessage());
    }


    @Test
    void deveRegistrarUsuarioComSucesso() {
        when(userRepository.existsByEmail("novo@exemplo.com")).thenReturn(false);
        when(passwordEncoder.encode("123")).thenReturn("senhaCriptografada");
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        User novo = authService.register("novo@exemplo.com", "123");

        assertEquals("novo@exemplo.com", novo.getEmail());
        assertEquals("senhaCriptografada", novo.getSenha());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void deveFalharAoRegistrarUsuarioDuplicado() {
        when(userRepository.existsByEmail("teste@exemplo.com")).thenReturn(true);

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                authService.register("teste@exemplo.com", "123")
        );

        assertEquals("Usuário já cadastrado", ex.getMessage());
    }
}
