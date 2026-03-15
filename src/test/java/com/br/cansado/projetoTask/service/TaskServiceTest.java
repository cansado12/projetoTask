package com.br.cansado.projetoTask.service;

import com.br.cansado.projetoTask.DTO.RequestDTO;
import com.br.cansado.projetoTask.DTO.ResponseDTO;
import com.br.cansado.projetoTask.exception.ResourceNotFoundException;
import com.br.cansado.projetoTask.mapper.Taskmapper;
import com.br.cansado.projetoTask.model.Status;
import com.br.cansado.projetoTask.model.Task;
import com.br.cansado.projetoTask.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    Taskmapper mapper;

    @Mock
    TaskRepository repository;

    @InjectMocks
    TaskService service;


    private Task task;
    private ResponseDTO responseDTO;
    private RequestDTO requestDTO;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        pageable = PageRequest.of(0, 5);

        task = new Task();
        task.setId(1L);
        task.setTitle("Estudar Spring Boot");
        task.setDescription("Estudar 2h por dia");
        task.setStatus(Status.PENDENTE);
        task.setCreatedAt(LocalDateTime.now());

        responseDTO = new ResponseDTO(
                1L, "Estudar Spring Boot", "Estudar 2h por dia", Status.PENDENTE, task.getCreatedAt()
        );

        requestDTO = new RequestDTO("Estudar Spring Boot", "Estudar 2h por dia", Status.PENDENTE);
    }

    // ----- findAll -----

    @Test
    @DisplayName("findAll - deve retornar página de DTOs")
    void findAll() {
        when(repository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(task)));
        when(mapper.toDTO(task)).thenReturn(responseDTO);

        Page<ResponseDTO> result = service.findAll(pageable);

        assertThat(result).hasSize(1);
        assertThat(result.getContent().get(0).title()).isEqualTo("Estudar Spring Boot");
        verify(repository).findAll(pageable);
    }

    // ----- findById -----

    @Test
    @DisplayName("findById - deve retornar DTO quando ID existe")
    void findById() {
        when(repository.findById(1L)).thenReturn(Optional.of(task));
        when(mapper.toDTO(task)).thenReturn(responseDTO);

        ResponseDTO result = service.findById(1L);

        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.title()).isEqualTo("Estudar Spring Boot");
    }

    @Test
    @DisplayName("findById - deve lançar exception quando ID não existe")
    void findById_idInexistente() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("ID nao encontrado");
    }

    // ----- update -----

    @Test
    @DisplayName("update - deve atualizar campos e retornar DTO")
    void update() {
        RequestDTO novosDados = new RequestDTO("Novo título", "Nova descrição", Status.EM_ANDAMENTO);
        ResponseDTO dtoAtualizado = new ResponseDTO(
                1L, "Novo título", "Nova descrição", Status.EM_ANDAMENTO, task.getCreatedAt()
        );

        when(repository.findById(1L)).thenReturn(Optional.of(task));
        when(repository.save(task)).thenReturn(task);
        when(mapper.toDTO(task)).thenReturn(dtoAtualizado);

        ResponseDTO result = service.update(1L, novosDados);

        assertThat(result.title()).isEqualTo("Novo título");
        assertThat(result.status()).isEqualTo(Status.EM_ANDAMENTO);
        verify(repository).save(task);
    }

    @Test
    @DisplayName("update - deve lançar exception quando ID não existe")
    void update_idInexistente() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(99L, requestDTO))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    // ----- delete -----

    @Test
    @DisplayName("delete - deve chamar deleteById quando ID existe")
    void delete() {
        when(repository.findById(1L)).thenReturn(Optional.of(task));

        service.delete(1L);

        verify(repository).deleteById(1L);
    }

    @Test
    @DisplayName("delete - deve lançar exception quando ID não existe")
    void delete_idInexistente() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.delete(99L))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(repository, never()).deleteById(any());
    }

    // ----- create -----

    @Test
    @DisplayName("create - deve salvar e retornar DTO")
    void create() {
        when(mapper.toEntity(requestDTO)).thenReturn(task);
        when(repository.save(any(Task.class))).thenReturn(task);
        when(mapper.toDTO(task)).thenReturn(responseDTO);

        ResponseDTO result = service.create(requestDTO);

        assertThat(result.title()).isEqualTo("Estudar Spring Boot");
        verify(repository).save(task);
    }

    @Test
    @DisplayName("create - deve setar status PENDENTE quando status for nulo")
    void create_statusNulo() {
        RequestDTO semStatus = new RequestDTO("Título", "Descrição", null);
        Task taskSemStatus = new Task();
        taskSemStatus.setStatus(null);

        when(mapper.toEntity(semStatus)).thenReturn(taskSemStatus);
        when(repository.save(any(Task.class))).thenReturn(taskSemStatus);
        when(mapper.toDTO(taskSemStatus)).thenReturn(responseDTO);

        service.create(semStatus);


        assertThat(taskSemStatus.getStatus()).isEqualTo(Status.PENDENTE);
    }

    @Test
    @DisplayName("create - deve preencher createdAt automaticamente")
    void create_createdAt() {
        Task taskSemData = new Task();
        taskSemData.setStatus(Status.PENDENTE);

        when(mapper.toEntity(requestDTO)).thenReturn(taskSemData);
        when(repository.save(any(Task.class))).thenReturn(taskSemData);
        when(mapper.toDTO(taskSemData)).thenReturn(responseDTO);

        service.create(requestDTO);

        assertThat(taskSemData.getCreatedAt()).isNotNull();
    }

    // ----- findByStatus -----

    @Test
    @DisplayName("findByStatus - deve retornar tasks filtradas pelo status")
    void findByStatus() {
        when(repository.findByStatus(Status.PENDENTE, pageable))
                .thenReturn(new PageImpl<>(List.of(task)));
        when(mapper.toDTO(task)).thenReturn(responseDTO);

        Page<ResponseDTO> result = service.findByStatus(Status.PENDENTE, pageable);

        assertThat(result).hasSize(1);
        assertThat(result.getContent().get(0).status()).isEqualTo(Status.PENDENTE);
    }
}