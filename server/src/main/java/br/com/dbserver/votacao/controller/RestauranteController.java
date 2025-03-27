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

import br.com.dbserver.votacao.model.Restaurante;
import br.com.dbserver.votacao.service.RestauranteService;

@RestController
@RequestMapping( "/api/restaurantes" )
@CrossOrigin( origins = "*" )
public class RestauranteController 
{

    @Autowired
    private RestauranteService service;
    
    @GetMapping
    public ResponseEntity<List<Restaurante>> findAll()
    {
        return ResponseEntity.ok( service.findAll() );
    }
    
    @GetMapping( "/disponiveis" )
    public ResponseEntity<List<Restaurante>> findDisponiveis() 
    {
        return ResponseEntity.ok( service.findRestaurantesDisponiveis() );
    }
    
    @GetMapping( "/{id}" )
    public ResponseEntity<Restaurante> findById(@PathVariable Long id) 
    {
        Optional<Restaurante> restaurante = service.findById( id );
    
        return restaurante.map( ResponseEntity::ok ).orElse( ResponseEntity.notFound().build() );
    }
    
    @PostMapping
    public ResponseEntity<Restaurante> create( @RequestBody Restaurante restaurante ) 
    {
        return ResponseEntity.status( HttpStatus.CREATED ).body( service.save( restaurante ) );
    }
    
    @PutMapping( "/{id}" )
    public ResponseEntity<Restaurante> update( @PathVariable Long id, @RequestBody Restaurante restaurante ) 
    {
        if ( ! service.findById( id ).isPresent() ) 
        {
            return ResponseEntity.notFound().build();
        }
        
        restaurante.setId( id );

        return ResponseEntity.ok( service.save( restaurante ) );
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