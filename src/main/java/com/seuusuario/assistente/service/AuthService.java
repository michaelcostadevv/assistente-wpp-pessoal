package com.seuusuario.assistente.service;

import com.seuusuario.assistente.dto.LoginRequestDTO;
import com.seuusuario.assistente.dto.RegistroRequestDTO;
import com.seuusuario.assistente.entity.Usuario;
import com.seuusuario.assistente.repository.UsuarioRepository;
import com.seuusuario.assistente.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    // 1. Cadastra um novo usuário com senha criptografada via BCrypt
    public Usuario registrar(RegistroRequestDTO dto) {
        if (usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("E-mail já cadastrado no sistema!");
        }

        Usuario usuario = new Usuario();
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(passwordEncoder.encode(dto.getSenha())); // Criptografa a senha!
        usuario.setTelefoneWhatsApp(dto.getTelefoneWhatsApp());

        return usuarioRepository.save(usuario);
    }

    // 2. Valida o login e devolve o Token JWT
    public String logar(LoginRequestDTO dto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getSenha())
        );

        // Se a autenticação passar sem erros, gera e retorna o token JWT
        return jwtService.gerarToken(dto.getEmail());
    }
}