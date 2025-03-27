package br.com.dbserver.votacao.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dbserver.votacao.model.Profissional;
import br.com.dbserver.votacao.service.ProfissionalService;

@RestController
@RequestMapping( "/api/profissionais" )
@CrossOrigin( origins = "*" )

public class ProfissionalController 
{
    @Autowired
    private ProfissionalService service;
    
    @GetMapping
    public ResponseEntity<List<Profissional>> findAll() 
    {
        return ResponseEntity.ok( service.findAll() );
    }
    
    @GetMapping( "/{id}" )
    public ResponseEntity<Profissional> findById( @PathVariable Long id ) 
    {
        Optional<Profissional> profissional = service.findById( id );

        return profissional.map( ResponseEntity::ok ).orElse( ResponseEntity.notFound().build() );
    }
    
    @PostMapping
    public ResponseEntity<Profissional> create( @RequestBody Profissional profissional ) 
    {
        return ResponseEntity.status( HttpStatus.CREATED ).body( service.save( profissional ) );
    }
    
    @PutMapping( "/{id}" )
    public ResponseEntity<Profissional> update( @PathVariable Long id, @RequestBody Profissional profissional ) 
    {
        if ( ! service.findById( id ).isPresent() ) 
        {
            return ResponseEntity.notFound().build();
        }
        
        profissional.setId( id );
        return ResponseEntity.ok( service.save( profissional ) );
    }
    
    @DeleteMapping( "/{id}" )
    public ResponseEntity<Void> delete( @PathVariable Long id ) 
    {
        if ( ! service.findById( id ).isPresent() ) 
        {
            return ResponseEntity.notFound().build();
        }
        
        service.delete( id );

        return ResponseEntity.noContent().build();
    }
} 