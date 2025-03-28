package br.com.dbserver.votacao.messaging.producer;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import br.com.dbserver.votacao.model.Voto;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@ConditionalOnProperty( name = "app.messaging.enabled", havingValue = "false", matchIfMissing = false )
public class VotoProducerMock 
{
    public void enviarMensagemVoto( Voto voto ) 
    {
        log.info( "MOCK: Mensageria desabilitada. Simulando envio de mensagem para o voto do profissional {} no restaurante {}.", 
                  voto.getProfissional().getNome(), voto.getRestaurante().getNome() );
    }
} 