package com.seuusuario.assistente.dto;

import lombok.Data;

@Data
public class RegistroRequestDTO {
    private String email;
    private String senha;
    private String telefoneWhatsApp;
}