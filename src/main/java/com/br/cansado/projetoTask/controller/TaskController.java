package com.br.cansado.projetoTask.controller;


import com.br.cansado.projetoTask.model.Task;
import com.br.cansado.projetoTask.service.TaskyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {


    private final TaskyService taskyService;
    public TaskController(TaskyService taskyService) {
        this.taskyService = taskyService;

    }

    @GetMapping
    public List<Task> findAll()
    {
        return taskyService.findAll();
    }


    @GetMapping("/{id}")
    public Task findById(@PathVariable long id) {
        return taskyService.FindById(id);

    }


    @PostMapping
    public ResponseEntity<Task> created(@RequestBody Task task) {
        Task task1 = taskyService.create(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(task1);


    }

    @PutMapping
    public ResponseEntity<Task> update(@RequestBody Task task) {
        Task atualizar = taskyService.atualizar(task);
        return ResponseEntity.ok(atualizar);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        taskyService.deletar(id);
        return ResponseEntity.noContent().build();
    }







}
