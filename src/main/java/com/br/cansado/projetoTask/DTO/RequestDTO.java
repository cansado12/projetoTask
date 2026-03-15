package com.br.cansado.projetoTask.DTO;

import com.br.cansado.projetoTask.model.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RequestDTO(@NotBlank(message = "Titulo obrigatorio") String title, @NotBlank(message = "Descriçao obrigatoria") String description, @NotNull(message = "Status obrigatorio") Status status) {
}
