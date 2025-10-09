package com.br.todoapi.todo_api.controller;

import com.br.todoapi.todo_api.dto.request.TaskCreateRequest;
import com.br.todoapi.todo_api.dto.request.TaskUpdateRequest;
import com.br.todoapi.todo_api.dto.response.TaskResponse;
import com.br.todoapi.todo_api.entity.Task;
import com.br.todoapi.todo_api.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;



@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@Tag(name = "Tasks", description = "Endpoints para gerenciamento de tarefas")
public class TaskController {

    @Autowired
    private final TaskService taskService;

     @GetMapping("/{id}")
     @Operation(summary = "Buscar tarefa por ID", description = "Retorna uma tarefa específica pelo seu ID")
     @ApiResponses(value = {
             @ApiResponse(responseCode = "200", description = "Tarefa encontrada com sucesso"),
             @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")
     })
    public ResponseEntity<TaskResponse> findById(@PathVariable Long id) {
        Task taskFind = taskService.findById(id);
        TaskResponse response = new TaskResponse(taskFind);
        return ResponseEntity.ok(response);
       }

    @GetMapping
    @Operation(summary = "Listar todas as tarefas", description = "Retorna todas as tarefas ordenadas por prioridade e data")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    public ResponseEntity<List<TaskResponse>> findAll() {
        List<Task> tasks = taskService.findAll();
        List<TaskResponse> response = tasks.stream()
                .map(TaskResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }


    @PostMapping
    @Operation(summary = "Criar nova tarefa", description = "Cria uma nova tarefa no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tarefa criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
   public ResponseEntity<TaskResponse> create(@Valid @RequestBody TaskCreateRequest request) {
        Task task = taskService.create(
                request.getNome(),
                request.getDescricao(),
                request.getPrioridade()
        );
        TaskResponse response = new TaskResponse(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PutMapping("/{id}")
    @Operation(summary = "Atualizar tarefa", description = "Atualiza os dados de uma tarefa existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefa atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<TaskResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody TaskUpdateRequest request
    ) {
        Task task = taskService.update(
                id,
                request.getNome(),
                request.getDescricao(),
                request.getPrioridade()
        );
        TaskResponse response = new TaskResponse(task);
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar tarefa", description = "Desativa uma tarefa (soft delete)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tarefa deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }


    @PatchMapping("/{id}/toggle")
    @Operation(summary = "Alternar status de conclusão", description = "Marca ou desmarca uma tarefa como realizada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status alterado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")
    })
    public ResponseEntity<TaskResponse> toggleRealizado(@PathVariable Long id) {
            Task task = taskService.check(id);
            TaskResponse response = new TaskResponse(task);
            return ResponseEntity.ok(response);
    }
    }

