package com.br.todoapi.todo_api.service;


import com.br.todoapi.todo_api.entity.Task;
import com.br.todoapi.todo_api.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;

    @Transactional(readOnly = true)
    public Task findById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada com ID: " + id));
    }

    @Transactional(readOnly = true)
    public List<Task> findAll() {
        Sort sort = Sort.by("realizado").ascending()
                .and(Sort.by("prioridade").descending())
                .and(Sort.by("dataCriacao").ascending());
        return taskRepository.findAll(sort);
    }

    public Task create(String nome, String descricao, Integer prioridade) {

        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
        if (prioridade < 0 || prioridade > 2) {
            throw new IllegalArgumentException("Prioridade deve ser entre 0 e 2");
        }

        Task newTask = new Task(nome, descricao, prioridade);
        return taskRepository.save(newTask);
    }

    public Task update(Long id, String nome, String descricao, Integer prioridade) {
        Task task = findById(id);

        if (!task.getAtivo()) {
            throw new IllegalStateException("Não é possível atualizar uma tarefa desativada");
        }

        if (nome != null && !nome.trim().isEmpty()) {
            task.setNome(nome);
        }
        if (descricao != null) {
            task.setDescricao(descricao);
        }
        if (prioridade != null && prioridade >= 0 && prioridade <= 2) {
            task.setPrioridade(prioridade);
        }

        return taskRepository.save(task);
    }

    public void delete(Long id) {
        Task taskFind = findById(id);
        taskFind.desativar();
        taskRepository.save(taskFind);
    }

    public Task check(long id) {
        Task taskFind = findById(id);
        if (!taskFind.getAtivo()) {
            throw new IllegalStateException("Não é possível modificar uma tarefa desativada");
        }

        if (taskFind.getRealizado()) {
            taskFind.desmacarComoRealizada();
        } else {
            taskFind.marcarComoRealizada();
        }
       return taskRepository.save(taskFind);
    }
}
