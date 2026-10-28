package com.seuusuario.assistente.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CompromissoRequestDTO {

    @NotBlank(message = "O título do compromisso não pode estar em branco")
    private String titulo;

    private String descricao;

    @NotNull(message = "A data e hora do compromisso são obrigatórias")
    @Future(message = "A data do compromisso deve ser no futuro")
    private LocalDateTime dataHora;
}