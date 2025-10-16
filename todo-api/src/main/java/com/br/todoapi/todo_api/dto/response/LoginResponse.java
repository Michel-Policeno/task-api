package com.br.todoapi.todo_api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private String token;
    private String type = "Bearer";
    private String message;

    public LoginResponse(String token, String message) {
        this.token = token;
        this.message = message;
    }
}
