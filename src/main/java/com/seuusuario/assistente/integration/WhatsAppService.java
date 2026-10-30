package com.seuusuario.assistente.integration;

import com.seuusuario.assistente.dto.WhatsAppMessageDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class WhatsAppService {

    private final RestClient restClient;

    // Lemos a URL da API e o Token de acesso diretamente das variáveis de ambiente no application.properties
    public WhatsAppService(@Value("${whatsapp.api.url}") String apiUrl,
                           @Value("${whatsapp.api.token}") String apiToken) {
        this.restClient = RestClient.builder()
                .baseUrl(apiUrl)
                .defaultHeader("Authorization", "Bearer " + apiToken)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    public void enviarMensagem(String telefone, String textoMensagem) {
        WhatsAppMessageDTO payload = new WhatsAppMessageDTO(telefone, textoMensagem);

        try {
            restClient.post()
                    .uri("/send-message") // Rota padrão da API de WhatsApp escolhida
                    .body(payload)
                    .retrieve()
                    .toBodilessEntity();

            System.out.println("Mensagem enviada com sucesso para: " + telefone);
        } catch (Exception e) {
            System.err.println("Erro ao enviar mensagem para o WhatsApp: " + e.getMessage());
            // Aqui podemos tratar a exceção ou re-lançar para o Handler global se necessário
        }
    }
}
