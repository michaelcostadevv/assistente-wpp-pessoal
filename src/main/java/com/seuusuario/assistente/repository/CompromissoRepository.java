package com.seuusuario.assistente.repository;

import com.seuusuario.assistente.entity.Compromisso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface CompromissoRepository extends JpaRepository<Compromisso, Long> {
    // O Spring Data JPA já implementa métodos prontos como save(), findById(), findAll(), delete()

    // Método que busca se já existe um compromisso com o mesmo título e data/hora exatos
    Optional<Compromisso> findByTituloAndDataHora(String titulo, LocalDateTime dataHora);
}