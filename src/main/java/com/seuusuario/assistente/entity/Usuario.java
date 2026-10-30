package com.seuusuario.assistente.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String senha; // Armazenada com criptografia BCrypt

    @Column(nullable = false)
    private String telefoneWhatsApp; // Essencial para o assistente mandar as mensagens
}