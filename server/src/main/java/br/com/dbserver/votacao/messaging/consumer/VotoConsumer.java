package br.com.dbserver.votacao.messaging.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import br.com.dbserver.votacao.messaging.dto.VotoMessage;
import br.com.dbserver.votacao.messaging.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class VotoConsumer 
{
    private final EmailService emailService;

    @KafkaListener( topics = "${app.kafka.topics.voto}", groupId = "${spring.kafka.consumer.group-id}" )
    public void consumirVotoMessage( VotoMessage message ) 
    {
        log.info( "Mensagem recebida: {}", message );
        
        try 
        {
            emailService.enviarEmailConfirmacaoVoto( message );
        } 
        
        catch ( Exception e ) 
        {
            log.error( "Erro ao processar mensagem de voto: {}", e.getMessage(), e );
        }
    }
} 