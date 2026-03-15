package com.br.cansado.projetoTask.DTO;

import com.br.cansado.projetoTask.model.Status;

import java.time.LocalDateTime;

public record ResponseDTO(Long id, String title, String description, Status status, LocalDateTime createdAt) {


}
