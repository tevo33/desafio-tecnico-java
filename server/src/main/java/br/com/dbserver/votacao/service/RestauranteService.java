package br.com.dbserver.votacao.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dbserver.votacao.model.Restaurante;
import br.com.dbserver.votacao.repository.RestauranteRepository;

@Service
public class RestauranteService 
{
    @Autowired
    private RestauranteRepository repository;
    
    public List<Restaurante> findAll() 
    {
        return repository.findAll();
    }
    
    public Optional<Restaurante> findById( Long id ) 
    {
        return repository.findById( id );
    }
    
    public Restaurante save( Restaurante restaurante ) 
    {
        return repository.save( restaurante );
    }
    
    public void delete( Long id ) 
    {
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