package br.com.dbserver.votacao.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.dbserver.votacao.messaging.producer.VotoProducer;
import br.com.dbserver.votacao.messaging.producer.VotoProducerMock;
import br.com.dbserver.votacao.model.Profissional;
import br.com.dbserver.votacao.model.Restaurante;
import br.com.dbserver.votacao.model.ResultadoVotacao;
import br.com.dbserver.votacao.model.Voto;
import br.com.dbserver.votacao.repository.RestauranteRepository;
import br.com.dbserver.votacao.repository.ResultadoVotacaoRepository;
import br.com.dbserver.votacao.repository.VotoRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class VotacaoService 
{
    @Autowired
    private VotoRepository votoRepository;
    
    @Autowired
    private RestauranteRepository restauranteRepository;
    
    @Autowired
    private ResultadoVotacaoRepository resultadoRepository;
    
    @Autowired(required = false)
    private VotoProducer votoProducer;
    
    @Autowired(required = false)
    private VotoProducerMock votoProducerMock;
    
    @Transactional
    public Voto votar(Voto voto) throws Exception 
    {
        log.info("Registrando voto do profissional ID {} para o restaurante ID {}", 
                voto.getProfissional().getId(), voto.getRestaurante().getId());
        
        if ( votoRepository.existsByProfissionalAndData( voto.getProfissional(), LocalDate.now() ) ) 
        {
            log.warn("Profissional ID {} já votou hoje", voto.getProfissional().getId());
            throw new Exception( "Profissional já votou hoje!" );
        }
        
        voto.setData( LocalDate.now() );
        
        Voto votoSalvo = votoRepository.save( voto );
        log.info("Voto registrado com sucesso: {}", votoSalvo);
        
        // Enviar mensagem usando o produtor apropriado
        enviarMensagemVoto(votoSalvo);
        
        return votoSalvo;
    }
    
    public ResultadoVotacao getResultadoVotacao() 
    {
        LocalDate hoje = LocalDate.now();

        Optional<ResultadoVotacao> resultadoOptional = resultadoRepository.findByData( hoje );
        
        if ( resultadoOptional.isPresent() ) 
        {
            return resultadoOptional.get();
        }

        if ( LocalTime.now().isBefore( LocalTime.of( 12, 0 ) ) ) 
        {
            List<Object[]> votos = votoRepository.countVotosByRestauranteAndData( hoje );
            
            if ( ! votos.isEmpty() ) 
            {
                Object[] maisVotado = votos.get( 0 );
                Long restauranteId = (Long) maisVotado[0];
                Long countVotos = (Long) maisVotado[1];
                
                Optional<Restaurante> restauranteOptional = restauranteRepository.findById( restauranteId );
                
                if ( restauranteOptional.isPresent() ) 
                {
                    ResultadoVotacao resultado = new ResultadoVotacao();

                    resultado.setRestaurante( restauranteOptional.get() );
                    resultado.setData( hoje );
                    resultado.setQuantidadeVotos( countVotos.intValue() );
                    
                    return resultadoRepository.save( resultado );
                }
            }
        }
        
        return null;
    }
    
    public List<Voto> findVotosByData( LocalDate data ) 
    {
        return votoRepository.findByData( data );
    }
    
    private void enviarMensagemVoto(Voto voto) {
        if (votoProducer != null) {
            log.info("Usando VotoProducer para enviar mensagem");
            votoProducer.enviarMensagemVoto(voto);
        } else if (votoProducerMock != null) {
            log.info("Usando VotoProducerMock para enviar mensagem");
            votoProducerMock.enviarMensagemVoto(voto);
        } else {
            log.warn("Nenhum produtor de voto configurado (nem real nem mock)");
        }
    }
} 