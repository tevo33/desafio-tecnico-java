package br.com.dbserver.votacao.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.dbserver.votacao.exception.ResourceNotFoundException;
import br.com.dbserver.votacao.model.Restaurante;
import br.com.dbserver.votacao.repository.RestauranteRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RestauranteService
{
    private final RestauranteRepository repository;
    
    public List<Restaurante> findAll() 
    {
        return repository.findAll();
    }
    
    public Restaurante findById( Long id )
    {
        return repository.findById( id )
                         .orElseThrow( () -> new ResourceNotFoundException( "Restaurante não encontrado com ID: " + id ) );
    }
    
    public Restaurante save( Restaurante restaurante )
    {
        return repository.save( restaurante );
    }
    
    public void delete( Long id )
    {
        if ( ! repository.existsById( id ) ) 
        {
            throw new ResourceNotFoundException( "Restaurante não encontrado com ID: " + id );
        }

        repository.deleteById( id );
    }
    
    public List<Restaurante> findRestaurantesDisponiveis() 
    {
        LocalDate hoje = LocalDate.now();
        LocalDate inicioSemana = hoje.with( TemporalAdjusters.previousOrSame( DayOfWeek.MONDAY ) );
        LocalDate fimSemana = hoje.with( TemporalAdjusters.nextOrSame( DayOfWeek.FRIDAY ) );
        
        return repository.findRestaurantesNaoEscolhidosNaSemana( inicioSemana, fimSemana );
    }
} 