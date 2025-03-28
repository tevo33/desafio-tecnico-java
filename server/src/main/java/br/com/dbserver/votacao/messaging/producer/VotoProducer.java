package br.com.dbserver.votacao.messaging.producer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import br.com.dbserver.votacao.messaging.dto.VotoMessage;
import br.com.dbserver.votacao.model.Voto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@ConditionalOnProperty( name = "app.messaging.enabled", havingValue = "true" )
public class VotoProducer
{
    private final KafkaTemplate<String, VotoMessage> kafkaTemplate;
    
    @Value( "${app.kafka.topics.voto}" )
    private String votoTopic;
    
    @Value( "${app.messaging.enabled}" )
    private boolean messagingEnabled;

    public void enviarMensagemVoto( Voto voto )
    {
        if ( ! messagingEnabled )
        {
            log.info( "Mensageria desabilitada. Ignorando envio de mensagem para o voto do profissional {}.", 
                      voto.getProfissional().getNome() );
            
            return;
        }
        
        VotoMessage message = new VotoMessage( voto.getProfissional().getId(),
                                               voto.getProfissional().getNome(),
                                               voto.getProfissional().getEmail(),
                                               voto.getRestaurante().getId(),
                                               voto.getRestaurante().getNome(),
                                               voto.getData()
        );
        
        log.info( "Enviando mensagem para o tópico {}: {}", votoTopic, message );
        
        kafkaTemplate.send( votoTopic, String.valueOf( voto.getProfissional().getId() ), message )
                     .thenAccept( result -> log.info( "Mensagem enviada com sucesso para o tópico {}", votoTopic ) )
                     .exceptionally( ex -> 
                     {
                         log.error( "Falha ao enviar mensagem para o tópico {}: {}", votoTopic, ex.getMessage() );
                         return null;
                     });
    }
}