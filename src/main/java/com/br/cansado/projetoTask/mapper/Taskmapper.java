package com.br.cansado.projetoTask.mapper;

import com.br.cansado.projetoTask.DTO.RequestDTO;
import com.br.cansado.projetoTask.DTO.ResponseDTO;
import com.br.cansado.projetoTask.model.Task;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface Taskmapper {

    Task toEntity(RequestDTO dto);


    ResponseDTO toDTO(Task task);


    List<Task> toEntityList(List<RequestDTO> dtoList);
    List<ResponseDTO> toDTOList(List<Task> taskList);


}
