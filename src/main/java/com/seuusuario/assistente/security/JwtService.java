package com.seuusuario.assistente.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    // Chave secreta usada para assinar o token (em produção, deve vir de uma variável de ambiente bem protegida)
    private static final String SECRET_KEY = "sua-chave-secreta-super-segura-e-longa-para-o-jwt-assistente";

    // Converte a chave em um objeto de criptografia seguro do JJWT
    private Key getSignInKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // 1. Gera o Token JWT baseado no e-mail do usuário
    public String gerarToken(String emailUsuario) {
        return Jwts.builder()
                .setSubject(emailUsuario)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Validade de 10 horas
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // 2. Extrai o e-mail (username) de dentro do token
    public String extrairEmail(String token) {
        return extrairClaim(token, Claims::getSubject);
    }

    // 3. Método genérico para extrair qualquer dado de dentro do token
    public <T> T extrairClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extrairTodasClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extrairTodasClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // 4. Valida se o token pertence ao usuário correto e se não está expirado
    public boolean tokenValido(String token, String emailUsuario) {
        final String emailDoToken = extrairEmail(token);
        return (emailDoToken.equals(emailUsuario)) && !isTokenExpirado(token);
    }

    private boolean isTokenExpirado(String token) {
        return extrairClaim(token, Claims::getExpiration).before(new Date());
    }
}