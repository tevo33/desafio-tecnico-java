package br.com.dbserver.votacao.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dbserver.votacao.model.Profissional;
import br.com.dbserver.votacao.repository.ProfissionalRepository;

@Service
public class ProfissionalService 
{
    @Autowired
    private ProfissionalRepository repository;
    
    public List<Profissional> findAll() 
    {
        return repository.findAll();
    }
    
    public Optional<Profissional> findById( Long id ) 
    {
        return repository.findById( id );
    }
    
    public Profissional save( Profissional profissional ) 
    {
        return repository.save( profissional );
    }
    
    public void delete( Long id ) 
    {
        repository.deleteById( id );
    }
} 