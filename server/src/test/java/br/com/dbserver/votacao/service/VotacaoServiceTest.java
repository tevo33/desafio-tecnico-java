package br.com.dbserver.votacao.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.dbserver.votacao.model.Profissional;
import br.com.dbserver.votacao.model.Restaurante;
import br.com.dbserver.votacao.model.ResultadoVotacao;
import br.com.dbserver.votacao.model.Voto;
import br.com.dbserver.votacao.repository.RestauranteRepository;
import br.com.dbserver.votacao.repository.ResultadoVotacaoRepository;
import br.com.dbserver.votacao.repository.VotoRepository;

public class VotacaoServiceTest {

    @Mock
    private VotoRepository votoRepository;
    
    @Mock
    private RestauranteRepository restauranteRepository;
    
    @Mock
    private ResultadoVotacaoRepository resultadoRepository;
    
    @InjectMocks
    private VotacaoService votacaoService;
    
    private Profissional profissional;
    private Restaurante restaurante;
    private Voto voto;
    
    @BeforeEach
    public void setup() 
    {
        MockitoAnnotations.openMocks( this );
        
        profissional = new Profissional( 1L, "Teste", "teste@dbserver.com.br" );
        restaurante = new Restaurante( 1L, "Restaurante Teste", "Endereço Teste", "Tipo Teste" );
        
        voto = new Voto();
        voto.setProfissional( profissional );
        voto.setRestaurante( restaurante );
    }
    
    @Test
    public void testVotarComSucesso() throws Exception 
    {
        when( votoRepository.existsByProfissionalAndData( any( Profissional.class ), any( LocalDate.class ) ) ).thenReturn( false );
        when( votoRepository.save( any( Voto.class ) ) ).thenReturn( voto );
        
        Voto resultado = votacaoService.votar( voto );
        
        assertEquals( voto, resultado );
        assertEquals( LocalDate.now(), resultado.getData() );
    }
    
    @Test
    public void testVotarProfissionalJaVotou() 
    {
        when( votoRepository.existsByProfissionalAndData( any( Profissional.class ), any( LocalDate.class ) ) ).thenReturn( true );
        
        Exception exception = assertThrows( Exception.class, () -> 
        {
            votacaoService.votar( voto );
        });
        
        assertEquals( "Profissional já votou hoje!", exception.getMessage() );
    }
    
    @Test
    public void testGetResultadoVotacaoExistente() 
    {
        ResultadoVotacao resultadoEsperado = new ResultadoVotacao( 1L, restaurante, LocalDate.now(), 5 );
        
        when(resultadoRepository.findByData( any( LocalDate.class ) ) ).thenReturn( Optional.of ( resultadoEsperado ) );
        
        ResultadoVotacao resultado = votacaoService.getResultadoVotacao();
        
        assertEquals( resultadoEsperado, resultado );
    }
} 