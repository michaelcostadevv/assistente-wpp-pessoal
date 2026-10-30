package com.seuusuario.assistente.controller;

import com.seuusuario.assistente.dto.LoginRequestDTO;
import com.seuusuario.assistente.dto.RegistroRequestDTO;
import com.seuusuario.assistente.entity.Usuario;
import com.seuusuario.assistente.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/registrar")
    public ResponseEntity<Usuario> registrar(@RequestBody RegistroRequestDTO dto) {
        Usuario novoUsuario = authService.registrar(dto);
        return ResponseEntity.ok(novoUsuario);
    }

    @PostMapping("/login")
    public ResponseEntity<String> logar(@RequestBody LoginRequestDTO dto) {
        String token = authService.logar(dto);
        return ResponseEntity.ok(token);
    }
}
