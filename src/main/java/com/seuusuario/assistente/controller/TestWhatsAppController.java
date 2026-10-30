package com.seuusuario.assistente.controller;

import com.seuusuario.assistente.integration.WhatsAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestWhatsAppController {

    @Autowired
    private WhatsAppService whatsAppService;

    @GetMapping("/test-wpp")
    public ResponseEntity<String> testarEnvio(
            @RequestParam String numero,
            @RequestParam String mensagem) {

        whatsAppService.enviarMensagem(numero, mensagem);
        return ResponseEntity.ok("Tentativa de envio efetuada para o número: " + numero);
    }
}