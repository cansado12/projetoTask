package com.br.cansado.projetoTask.service;


import com.br.cansado.projetoTask.DTO.RequestDTO;
import com.br.cansado.projetoTask.DTO.ResponseDTO;
import com.br.cansado.projetoTask.exception.ResourceNotFoundException;
import com.br.cansado.projetoTask.mapper.Taskmapper;
import com.br.cansado.projetoTask.model.Status;
import com.br.cansado.projetoTask.model.Task;
import com.br.cansado.projetoTask.repository.TaskRepository;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TaskService {
    private final Taskmapper taskmapper;

    TaskRepository taskRepository;

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(TaskService.class);

    public TaskService(Taskmapper taskmapper, TaskRepository taskyRepository) {
        this.taskmapper = taskmapper;
        this.taskRepository = taskyRepository;
    }


    public Page<ResponseDTO> findAll(Pageable pageable) {
        return taskRepository.findAll(pageable).map(taskmapper::toDTO);
    }


    public ResponseDTO findById(Long id) {
        logger.info("Buscando o task com o id: " + id);
        var dto = taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ID nao encontrado"));
        return taskmapper.toDTO(dto);
    }

    public ResponseDTO update(Long id, RequestDTO task) {
        logger.info("Atualizando o task com o id: " + id);
        Task entity = taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ID nao encontrado"));
        entity.setTitle(task.title());
        entity.setDescription(task.description());
        entity.setStatus(task.status());
        var save = taskRepository.save(entity);
        return taskmapper.toDTO(save);

    }

    public void delete(Long id) {
        logger.info("Deletando o task");
        taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ID nao encontrado"));
        taskRepository.deleteById(id);
    }


    public ResponseDTO create(RequestDTO task) {
        logger.info("Criando o task");
        var entity = taskmapper.toEntity(task);
        if (entity.getStatus() == null) {
            entity.setStatus(Status.PENDENTE);
        }
        entity.setCreatedAt(LocalDateTime.now());
        var save = taskRepository.save(entity);
        return taskmapper.toDTO(save);

    }



    public Page<ResponseDTO> findByStatus(Status status, Pageable pageable) {
        return taskRepository.findByStatus(status, pageable)
                .map(taskmapper::toDTO);
    }













}
