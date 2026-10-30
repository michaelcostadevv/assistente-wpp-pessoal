package com.seuusuario.assistente.service;

import com.seuusuario.assistente.entity.Compromisso;
import com.seuusuario.assistente.entity.Usuario;
import com.seuusuario.assistente.integration.WhatsAppService;
import com.seuusuario.assistente.repository.CompromissoRepository;
import com.seuusuario.assistente.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class CompromissoService {

    @Autowired
    private CompromissoRepository compromissoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private WhatsAppService whatsAppService;

    public Compromisso salvar(Compromisso compromisso, String emailUsuarioLogado) {
        // 1. Busca o usuário logado no banco pelo e-mail obtido do Token JWT
        Usuario usuario = usuarioRepository.findByEmail(emailUsuarioLogado)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        // 2. Vincula o compromisso ao usuário
        compromisso.setUsuario(usuario);

        // 3. Validação de duplicidade específica para este usuário
        Optional<Compromisso> existente = compromissoRepository.findByTituloAndDataHoraAndUsuario(
                compromisso.getTitulo(),
                compromisso.getDataHora(),
                usuario
        );

        if (existente.isPresent()) {
            throw new RuntimeException("Já existe um compromisso idêntico cadastrado para esta data e horário!");
        }

        // 4. Salva no banco de dados PostgreSQL
        Compromisso novoCompromisso = compromissoRepository.save(compromisso);

        // 5. Dispara a mensagem de confirmação usando o WhatsApp cadastrado do próprio usuário
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String dataFormatada = novoCompromisso.getDataHora().format(formatter);

        String mensagem = "🤖 *Assistente Pessoal*\n\n" +
                "✅ Compromisso agendado com sucesso!\n" +
                "📌 *Título:* " + novoCompromisso.getTitulo() + "\n" +
                "🕒 *Data/Hora:* " + dataFormatada;

        if (usuario.getTelefoneWhatsApp() != null) {
            whatsAppService.enviarMensagem(usuario.getTelefoneWhatsApp(), mensagem);
        }

        return novoCompromisso;
    }

    public List<Compromisso> listarPorUsuario(String emailUsuarioLogado) {
        Usuario usuario = usuarioRepository.findByEmail(emailUsuarioLogado)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        return compromissoRepository.findByUsuario(usuario);
    }
}