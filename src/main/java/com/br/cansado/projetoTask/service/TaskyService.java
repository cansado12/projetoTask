package com.br.cansado.projetoTask.service;


import com.br.cansado.projetoTask.exception.ResourceNotFoundException;
import com.br.cansado.projetoTask.model.Task;
import com.br.cansado.projetoTask.repository.TaskyRepository;
import org.apache.logging.log4j.LogManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class TaskyService {

    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(TaskyService.class);
    TaskyRepository taskyRepository;
    private java.util.logging.Logger logger = Logger.getLogger(TaskyRepository.class.getName());

    public TaskyService(TaskyRepository taskyRepository) {
        this.taskyRepository = taskyRepository;
    }


    public List<Task>  findAll() {
        logger.info("Listando todos os tasks");
        return taskyRepository.findAll();
    }

    public Task FindById(Long id) {
        logger.info("Buscando o task com o id: " + id);
        return taskyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ID nao encontrado"));

    }

    public Task atualizar(Task task) {
        logger.info("Atualizando o task com o id: " + task.getId());
        taskyRepository.findById(task.getId()).orElseThrow(() -> new ResourceNotFoundException("ID nao encontrado"));
        task.setTitle(task.getTitle());
        task.setDescription(task.getDescription());
        task.setCreatedAt(task.getCreatedAt());
        task.setStatus(task.getStatus());
        return taskyRepository.save(task);
    }

    public void deletar(Long id) {
        logger.info("Deletando o task");
        taskyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ID nao encontrado"));
        taskyRepository.deleteById(id);
    }


    public Task create(Task task) {
        logger.info("Criando o task");
        return taskyRepository.save(task);
    }











}
