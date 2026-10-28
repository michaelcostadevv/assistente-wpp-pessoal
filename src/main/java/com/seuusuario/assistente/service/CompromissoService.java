package com.seuusuario.assistente.service;

import com.seuusuario.assistente.entity.Compromisso;
import com.seuusuario.assistente.repository.CompromissoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompromissoService {

    @Autowired
    private CompromissoRepository compromissoRepository;

    public Compromisso salvar(Compromisso compromisso) {
        // Validação de duplicidade: verifica se já existe compromisso igual
        Optional<Compromisso> existente = compromissoRepository.findByTituloAndDataHora(
                compromisso.getTitulo(),
                compromisso.getDataHora()
        );

        if (existente.isPresent()) {
            throw new RuntimeException("Já existe um compromisso idêntico cadastrado para esta data e horário!");
        }

        return compromissoRepository.save(compromisso);
    }

    public List<Compromisso> listarTodos() {
        return compromissoRepository.findAll();
    }
}