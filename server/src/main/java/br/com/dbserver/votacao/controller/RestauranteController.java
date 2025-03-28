package br.com.dbserver.votacao.controller;

import java.util.List;

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
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping( "/api/restaurantes" )
@CrossOrigin( origins = "*" )
@RequiredArgsConstructor
public class RestauranteController 
{
    private final RestauranteService service;
    
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
    public ResponseEntity<Restaurante> findById( @PathVariable Long id )
    {
        return ResponseEntity.ok( service.findById( id ) );
    }
    
    @PostMapping
    public ResponseEntity<Restaurante> create( @RequestBody Restaurante restaurante ) 
    {
        return ResponseEntity.status( HttpStatus.CREATED ).body( service.save( restaurante ) );
    }
    
    @PutMapping( "/{id}" )
    public ResponseEntity<Restaurante> update( @PathVariable Long id, @RequestBody Restaurante restaurante ) 
    {
        restaurante.setId( id );

        return ResponseEntity.ok( service.save( restaurante ) );
    }
    
    @DeleteMapping( "/{id}" )
    public ResponseEntity<Void> delete( @PathVariable Long id )
    {
        service.delete( id );

        return ResponseEntity.noContent().build();
    }
} 