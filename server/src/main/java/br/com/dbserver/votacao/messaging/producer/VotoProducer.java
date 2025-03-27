package br.com.dbserver.votacao.messaging.producer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import br.com.dbserver.votacao.messaging.dto.VotoMessage;
import br.com.dbserver.votacao.model.Voto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class VotoProducer {

    private final KafkaTemplate<String, VotoMessage> kafkaTemplate;
    
    @Value("${app.kafka.topics.voto}")
    private String votoTopic;

    public void enviarMensagemVoto(Voto voto) {
        VotoMessage message = new VotoMessage(
            voto.getProfissional().getId(),
            voto.getProfissional().getNome(),
            voto.getProfissional().getEmail(),
            voto.getRestaurante().getId(),
            voto.getRestaurante().getNome(),
            voto.getData()
        );
        
        log.info("Enviando mensagem para o tópico {}: {}", votoTopic, message);
        
        kafkaTemplate.send(votoTopic, String.valueOf(voto.getProfissional().getId()), message)
            .addCallback(
                result -> log.info("Mensagem enviada com sucesso para o tópico {}", votoTopic),
                ex -> log.error("Falha ao enviar mensagem para o tópico {}: {}", votoTopic, ex.getMessage())
            );
    }
} 