package com.br.todoapi.todo_api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nome;

    @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres")
    @Column(length = 500)
    private String descricao;

    @Min(value = 0, message = "Prioridade mínima é 0")
    @Max(value = 2, message = "Prioridade máxima é 2")
    @Column(nullable = false)
    private Integer prioridade = 0;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @Column(nullable = false)
    private Boolean realizado = false;

    private LocalDateTime dataRealizacao;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime ultimaModificacao;

    @Column(nullable = false)
    private Boolean ativo = true;


    public Task(String nome, String descricao, Integer prioridade) {
        this.nome = nome;
        this.descricao = descricao;
        this.prioridade = prioridade;
    }

    public void marcarComoRealizada() {
        this.realizado = true;
        this.dataRealizacao = LocalDateTime.now();
    }

    public void desmacarComoRealizada() {
        this.realizado = false;
        this.dataRealizacao = null;
    }

    public void desativar() {
        this.ativo = false;
    }
}