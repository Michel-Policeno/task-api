package com.br.todoapi.todo_api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Offer {
    private String type;
    private String name;
    private String url;
}