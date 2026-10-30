package com.seuusuario.assistente.repository;

import com.seuusuario.assistente.entity.Compromisso;
import com.seuusuario.assistente.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CompromissoRepository extends JpaRepository<Compromisso, Long> {

    // Método para validar duplicidade por Título, Data/Hora e Usuário específico
    Optional<Compromisso> findByTituloAndDataHoraAndUsuario(String titulo, LocalDateTime dataHora, Usuario usuario);

    // Método para listar todos os compromissos de um usuário específico
    List<Compromisso> findByUsuario(Usuario usuario);

    // Consulta para o Scheduler buscar compromissos pendentes globais que venceram
    @Query("SELECT c FROM Compromisso c WHERE c.notificado = false AND c.dataHora <= :agora")
    List<Compromisso> findCompromissosPendentes(@Param("agora") LocalDateTime agora);
}