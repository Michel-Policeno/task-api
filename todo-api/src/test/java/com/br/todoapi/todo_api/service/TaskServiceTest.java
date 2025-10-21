package com.br.todoapi.todo_api.service;

import com.br.todoapi.todo_api.entity.Task;
import com.br.todoapi.todo_api.entity.User;
import com.br.todoapi.todo_api.repository.TaskRepository;
import com.br.todoapi.todo_api.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private TaskService taskService;

    private User mockUser;

    @BeforeEach
    void setup() {
        //Inicializa todos os mocks antes de cada teste.
        MockitoAnnotations.openMocks(this);

        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setEmail("user@teste.com");
        mockUser.setAtivo(true);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("user@teste.com");
        SecurityContextHolder.setContext(securityContext);

        when(userRepository.findByEmail("user@teste.com")).thenReturn(Optional.of(mockUser));
    }

    // CREATE TASK
    @Test
    void deveCriarTarefaComSucesso() {
        Task taskEsperada = new Task("Teste", "Descrição", 1, mockUser);
        when(taskRepository.save(any(Task.class))).thenReturn(taskEsperada);

        Task result = taskService.create("Teste", "Descrição", 1);

        assertThat(result).isNotNull();
        assertThat(result.getNome()).isEqualTo("Teste");
        assertThat(result.getUser()).isEqualTo(mockUser);

        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void deveLancarErroQuandoNomeVazio() {
        assertThatThrownBy(() -> taskService.create(" ", "Desc", 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Nome não pode ser vazio");
    }

    @Test
    void deveLancarErroQuandoPrioridadeInvalida() {
        assertThatThrownBy(() -> taskService.create("Task", "Desc", 5))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Prioridade deve ser entre 0 e 2");
    }

    // FIND BY ID
    @Test
    void deveEncontrarTarefaPorId() {
        Task task = new Task("Task", "Desc", 1, mockUser);
        task.setId(1L);

        when(taskRepository.findByIdAndUser(1L, mockUser)).thenReturn(Optional.of(task));

        Task result = taskService.findById(1L);

        assertThat(result).isEqualTo(task);
        verify(taskRepository, times(1)).findByIdAndUser(1L, mockUser);
    }

    @Test
    void deveLancarErroQuandoTarefaNaoEncontrada() {
        when(taskRepository.findByIdAndUser(99L, mockUser)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskService.findById(99L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Tarefa não encontrada com ID: 99");
    }

    // FIND ALL
    @Test
    void deveListarTarefasAtivasDoUsuario() {
        Task t1 = new Task("Task 1", "Desc 1", 1, mockUser);
        Task t2 = new Task("Task 2", "Desc 2", 0, mockUser);

        when(taskRepository.findByUserAndAtivoTrue(eq(mockUser), any(Sort.class)))
                .thenReturn(List.of(t1, t2));

        List<Task> result = taskService.findAll();

        assertThat(result).hasSize(2);
        verify(taskRepository, times(1))
                .findByUserAndAtivoTrue(eq(mockUser), any(Sort.class));
    }

    // UPDATE
    @Test
    void deveAtualizarTarefaComSucesso() {
        Task task = new Task("Antiga", "Desc", 1, mockUser);
        task.setId(1L);
        task.setAtivo(true);

        when(taskRepository.findByIdAndUser(1L, mockUser)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task result = taskService.update(1L, "Nova", "Nova Desc", 2);

        assertThat(result.getNome()).isEqualTo("Nova");
        assertThat(result.getPrioridade()).isEqualTo(2);
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void deveLancarErroAoAtualizarTarefaDesativada() {
        Task task = new Task("Task", "Desc", 1, mockUser);
        task.setAtivo(false);
        when(taskRepository.findByIdAndUser(1L, mockUser)).thenReturn(Optional.of(task));

        assertThatThrownBy(() -> taskService.update(1L, "Nova", "Desc", 1))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Não é possível atualizar uma tarefa desativada");
    }

    // DELETE
    @Test
    void deveDesativarTarefaAoDeletar() {
        Task task = new Task("Task", "Desc", 1, mockUser);
        task.setId(1L);
        task.setAtivo(true);

        when(taskRepository.findByIdAndUser(1L, mockUser)).thenReturn(Optional.of(task));

        taskService.delete(1L);

        assertThat(task.getAtivo()).isFalse();
        verify(taskRepository, times(1)).save(task);
    }

    // CHECK TASK (ID/TOGGLE)
    @Test
    void deveMarcarTarefaComoRealizada() {
        Task task = new Task("Task", "Desc", 1, mockUser);
        task.setAtivo(true);
        task.setRealizado(false);

        when(taskRepository.findByIdAndUser(1L, mockUser)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task result = taskService.check(1L);

        assertThat(result.getRealizado()).isTrue();
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void deveDesmarcarTarefaComoRealizada() {
        Task task = new Task("Task", "Desc", 1, mockUser);
        task.setAtivo(true);
        task.setRealizado(true);

        when(taskRepository.findByIdAndUser(1L, mockUser)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task result = taskService.check(1L);

        assertThat(result.getRealizado()).isFalse();
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void deveLancarErroAoTentarCheckarTarefaInativa() {
        Task task = new Task("Task", "Desc", 1, mockUser);
        task.setAtivo(false);
        when(taskRepository.findByIdAndUser(1L, mockUser)).thenReturn(Optional.of(task));

        assertThatThrownBy(() -> taskService.check(1L))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Não é possível modificar uma tarefa desativada");
    }
}
