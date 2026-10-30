package com.seuusuario.assistente.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WhatsAppMessageDTO {
    private String phone;
    private String message;
}
