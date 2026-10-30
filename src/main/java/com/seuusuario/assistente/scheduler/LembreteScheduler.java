package com.seuusuario.assistente.scheduler;

import com.seuusuario.assistente.entity.Compromisso;
import com.seuusuario.assistente.integration.WhatsAppService;
import com.seuusuario.assistente.repository.CompromissoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class LembreteScheduler {

    @Autowired
    private CompromissoRepository compromissoRepository;

    @Autowired
    private WhatsAppService whatsAppService;

    // Roda a cada 1 minuto (60000 ms) automaticamente
    @Scheduled(fixedRate = 60000)
    public void verificarCompromissosPendentes() {
        LocalDateTime agora = LocalDateTime.now();

        // 1. Busca no banco todos os compromissos que venceram e não foram notificados
        List<Compromisso> pendentes = compromissoRepository.findCompromissosPendentes(agora);

        if (pendentes.isEmpty()) {
            return; // Se não há nada pendente, encerra a execução por enquanto
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        // 2. Percorre cada compromisso encontrado
        for (Compromisso compromisso : pendentes) {
            String dataFormatada = compromisso.getDataHora().format(formatter);

            // 3. Monta a mensagem de lembrete
            String mensagem = "⏰ *LEMBRETE DE COMPROMISSO*\n\n" +
                    "📌 *Título:* " + compromisso.getTitulo() + "\n" +
                    "📝 *Descrição:* " + (compromisso.getDescricao() != null ? compromisso.getDescricao() : "Nenhuma") + "\n" +
                    "🕒 *Horário:* " + dataFormatada;

            // Telefone de destino (exemplo)
            String telefoneDestino = "5571999999999";

            // 4. Dispara a mensagem via WhatsApp
            whatsAppService.enviarMensagem(telefoneDestino, mensagem);

            // 5. Atualiza o status para notificado = true para não mandar de novo
            compromisso.setNotificado(true);
            compromissoRepository.save(compromisso);

            System.out.println("Lembrete enviado com sucesso para o compromisso ID: " + compromisso.getId());
        }
    }
}