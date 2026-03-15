package com.br.cansado.projetoTask.controller;


import com.br.cansado.projetoTask.DTO.RequestDTO;
import com.br.cansado.projetoTask.DTO.ResponseDTO;
import com.br.cansado.projetoTask.model.Status;
import com.br.cansado.projetoTask.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Tasks", description = "Endpoints para gerenciamento de tasks")
@RestController
@RequestMapping("/task")
public class TaskController {




    private final TaskService taskService;
    public TaskController(TaskService taskyService) {
        this.taskService = taskyService;

    }

    @Operation(
            summary = "Lista todas as tasks",
            description = "Retorna uma lista paginada de todas as tasks cadastradas"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tasks encontradas com sucesso"),
         @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})
    public ResponseEntity<Page<ResponseDTO>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        return ResponseEntity.ok(taskService.findAll(pageable));


    }






    @Operation(
            summary = "Procura por ID",
            description = "retorna pelo ID da TASK"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Task encontrada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping(value = "/{id}",  produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})
    public ResponseEntity<ResponseDTO> findById(@PathVariable long id) {

        return ResponseEntity.ok(taskService.findById(id));


    }


    @Operation(
            summary = "Cria uma task",
            description = "Cria e adiciona uma task"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Task criada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})
    public ResponseEntity<ResponseDTO> created(@RequestBody @Valid RequestDTO resquestDTO) {
        var task1 = taskService.create(resquestDTO);
        return ResponseEntity.status(201).body(task1);


    }

    @Operation(
            summary = "Atualiza a task",
            description = "Atualiza as informaçoes de uma task"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Task atualizada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PutMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})
    public ResponseEntity<ResponseDTO> update(@PathVariable Long id,@Valid @RequestBody RequestDTO task) {


        ResponseDTO atualizar = taskService.update(id,task);
        return ResponseEntity.ok(atualizar);

    }

    @Operation(
            summary = "Deleta a task",
            description = "Deleta a task desejada pelo ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Task delatada"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }



    @Operation(
            summary = "Filtra tasks",
            description = "Filtra todas as tasks por status desejado"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tasks encontradas com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping(value = "/status/{status}",  produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})
    public ResponseEntity<Page<ResponseDTO>> findByStatus(@PathVariable Status status,
                                                          @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "id") String sortBy ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return ResponseEntity.ok(taskService.findByStatus(status,pageable));


    }


















}
