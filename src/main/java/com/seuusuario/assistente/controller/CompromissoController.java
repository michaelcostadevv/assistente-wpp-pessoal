package com.seuusuario.assistente.controller;

import com.seuusuario.assistente.dto.CompromissoRequestDTO;
import com.seuusuario.assistente.entity.Compromisso;
import com.seuusuario.assistente.mapper.CompromissoMapper;
import com.seuusuario.assistente.service.CompromissoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/compromissos")
public class CompromissoController {

    @Autowired
    private CompromissoService compromissoService;

    @Autowired
    private CompromissoMapper compromissoMapper;

    @PostMapping
    public ResponseEntity<Compromisso> criar(@RequestBody @Valid CompromissoRequestDTO requestDTO,
                                             Principal principal) {
        // principal.getName() captura automaticamente o e-mail que está dentro do Token JWT
        String emailUsuario = principal.getName();

        Compromisso compromisso = compromissoMapper.toEntity(requestDTO);
        Compromisso novoCompromisso = compromissoService.salvar(compromisso, emailUsuario);

        return ResponseEntity.ok(novoCompromisso);
    }

    @GetMapping
    public ResponseEntity<List<Compromisso>> listar(Principal principal) {
        String emailUsuario = principal.getName();
        List<Compromisso> lista = compromissoService.listarPorUsuario(emailUsuario);
        return ResponseEntity.ok(lista);
    }
}