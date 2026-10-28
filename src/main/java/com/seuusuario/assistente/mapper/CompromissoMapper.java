package com.seuusuario.assistente.mapper;

import com.seuusuario.assistente.dto.CompromissoRequestDTO;
import com.seuusuario.assistente.entity.Compromisso;
import org.springframework.stereotype.Component;

@Component
public class CompromissoMapper {

    // Converte de DTO para Entidade (usado na hora de criar)
    public Compromisso toEntity(CompromissoRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Compromisso compromisso = new Compromisso();
        compromisso.setTitulo(dto.getTitulo());
        compromisso.setDescricao(dto.getDescricao());
        compromisso.setDataHora(dto.getDataHora());
        return compromisso;
    }
}