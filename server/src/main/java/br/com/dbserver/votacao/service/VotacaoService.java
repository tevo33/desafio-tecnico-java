package br.com.dbserver.votacao.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.dbserver.votacao.exception.ResourceNotFoundException;
import br.com.dbserver.votacao.exception.VotacaoException;
import br.com.dbserver.votacao.messaging.producer.VotoProducer;
import br.com.dbserver.votacao.messaging.producer.VotoProducerMock;
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
    private final VotoRepository votoRepository;
    private final RestauranteRepository restauranteRepository;
    private final ResultadoVotacaoRepository resultadoRepository;
    private final VotoProducer votoProducer;
    private final VotoProducerMock votoProducerMock;
    
    public VotacaoService( VotoRepository votoRepository, RestauranteRepository restauranteRepository,
                           ResultadoVotacaoRepository resultadoRepository, @Nullable VotoProducer votoProducer,
                           @Nullable VotoProducerMock votoProducerMock) 
    {
        this.votoRepository = votoRepository;
        this.restauranteRepository = restauranteRepository;
        this.resultadoRepository = resultadoRepository;
        this.votoProducer = votoProducer;
        this.votoProducerMock = votoProducerMock;
    }
    
    @Transactional
    public Voto votar( Voto voto )
    {
        log.info( "Registrando voto do profissional ID {} para o restaurante ID {}", 
                  voto.getProfissional().getId(), voto.getRestaurante().getId() );
        
        if ( votoRepository.existsByProfissionalAndData( voto.getProfissional(), LocalDate.now() ) ) 
        {
            log.warn( "Profissional ID {} já votou hoje", voto.getProfissional().getId() );
            
            throw new VotacaoException( "Profissional já votou hoje!" );
        }
        
        voto.setData( LocalDate.now() );
        
        Voto votoSalvo = votoRepository.save( voto );
        log.info("Voto registrado com sucesso: {}", votoSalvo);
        
        enviarMensagemVoto( votoSalvo );
        
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
                
                Restaurante restaurante = restauranteRepository.findById( restauranteId )
                    .orElseThrow(() -> new ResourceNotFoundException( "Restaurante não encontrado com ID: " + restauranteId ) );
                
                ResultadoVotacao resultado = new ResultadoVotacao();
                resultado.setRestaurante( restaurante );
                resultado.setData( hoje );
                resultado.setQuantidadeVotos( countVotos.intValue() );

                return resultadoRepository.save( resultado );
            }
        }
        
        throw new ResourceNotFoundException( "Nenhum resultado de votação disponível para hoje" );
    }
    
    public List<Voto> findVotosByData( LocalDate data )
    {
        return votoRepository.findByData( data );
    }
    
    private void enviarMensagemVoto( Voto voto )
    {
        if ( votoProducer != null )
        {
            votoProducer.enviarMensagemVoto( voto );
        }

        else if ( votoProducerMock != null )
        {
            votoProducerMock.enviarMensagemVoto( voto );
        }
    }
} 