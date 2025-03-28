package br.com.dbserver.votacao.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.dbserver.votacao.exception.ResourceNotFoundException;
import br.com.dbserver.votacao.model.Profissional;
import br.com.dbserver.votacao.repository.ProfissionalRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfissionalService
{
    private final ProfissionalRepository repository;
    
    public List<Profissional> findAll() 
    {
        return repository.findAll();
    }
    
    public Profissional findById( Long id )
    {
        return repository.findById( id )
                         .orElseThrow( () -> new ResourceNotFoundException( "Profissional não encontrado com ID: " + id ) );
    }
    
    public Profissional save( Profissional profissional )
    {
        return repository.save( profissional );
    }
    
    public void delete( Long id )
    {
        if ( ! repository.existsById( id ) ) 
        {
            throw new ResourceNotFoundException( "Profissional não encontrado com ID: " + id );
        }

        repository.deleteById( id );
    }
} 