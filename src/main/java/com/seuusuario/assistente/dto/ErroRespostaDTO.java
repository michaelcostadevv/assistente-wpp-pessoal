package com.seuusuario.assistente.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErroRespostaDTO {
    private LocalDateTime timestamp;
    private int status;
    private String mensagem;
}