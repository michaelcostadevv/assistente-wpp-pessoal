package com.seuusuario.assistente.controller;

import com.seuusuario.assistente.dto.CompromissoRequestDTO;
import com.seuusuario.assistente.entity.Compromisso;
import com.seuusuario.assistente.mapper.CompromissoMapper;
import com.seuusuario.assistente.service.CompromissoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/compromissos")
public class CompromissoController {

    @Autowired
    private CompromissoService compromissoService;

    @Autowired
    private CompromissoMapper compromissoMapper;

    @PostMapping
    public ResponseEntity<Compromisso> criar(@RequestBody @Valid CompromissoRequestDTO requestDTO) {
        // 1. Converte o DTO para Entidade usando o Mapper
        Compromisso compromisso = compromissoMapper.toEntity(requestDTO);

        // 2. Salva usando o Service (que já tem a regra de duplicidade)
        Compromisso novoCompromisso = compromissoService.salvar(compromisso);

        // 3. Retorna a resposta HTTP 200 OK com o objeto criado
        return ResponseEntity.ok(novoCompromisso);
    }

    @GetMapping
    public ResponseEntity<List<Compromisso>> listar() {
        List<Compromisso> lista = compromissoService.listarTodos();
        return ResponseEntity.ok(lista);
    }
}