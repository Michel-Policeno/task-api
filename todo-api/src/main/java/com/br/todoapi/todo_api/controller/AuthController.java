package com.br.todoapi.todo_api.controller;

import com.br.todoapi.todo_api.dto.request.AuthRequest;
import com.br.todoapi.todo_api.dto.response.LoginResponse;
import com.br.todoapi.todo_api.dto.response.RegisterResponse;
import com.br.todoapi.todo_api.dto.response.TaskResponse;
import com.br.todoapi.todo_api.entity.Task;
import com.br.todoapi.todo_api.entity.User;
import com.br.todoapi.todo_api.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticação", description = "Endpoints de autenticação e registro")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @Operation(summary = "Registrar novo usuário", description = "Cria um novo usuário no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RegisterResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou usuário já existe",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                        {
                          "status": 400,
                          "error": "Bad Request",
                          "message": "Usuário já cadastrado com este e-mail"
                        }
                        """)))
    })
    public ResponseEntity<RegisterResponse> createdUser(@RequestBody AuthRequest request) {
        User userCreated = authService.register(request.getEmail(), request.getSenha());
        RegisterResponse response = new RegisterResponse(userCreated.getEmail(), "Usuário criado com sucesso");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PostMapping("/login")
    @Operation(summary = "Fazer login", description = "Autentica um usuário e retorna token JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = """
                        {
                          "status": 401,
                          "error": "Unauthorized",
                          "message": "E-mail ou senha incorretos"
                        }
                        """)))
    })
       public ResponseEntity<LoginResponse> login(@RequestBody AuthRequest request) {
        String token = authService.login(request.getEmail(), request.getSenha());
        LoginResponse response = new LoginResponse(token, "Login realizado com sucesso");
        return ResponseEntity.ok(response);
    }
}
