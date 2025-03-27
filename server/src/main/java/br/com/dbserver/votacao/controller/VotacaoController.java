package br.com.dbserver.votacao.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dbserver.votacao.model.ResultadoVotacao;
import br.com.dbserver.votacao.model.Voto;
import br.com.dbserver.votacao.service.VotacaoService;

@RestController
@RequestMapping( "/api/votacao" )
@CrossOrigin( origins = "*" )
public class VotacaoController 
{
    @Autowired
    private VotacaoService service;
    
    @PostMapping( "/votar" )
    public ResponseEntity<?> votar( @RequestBody Voto voto )
    {
        try 
        {
            return ResponseEntity.status( HttpStatus.CREATED ).body( service.votar( voto ) );
        } 
        
        catch ( Exception e ) 
        {
            return ResponseEntity.badRequest().body( e.getMessage() );
        }
    }
    
    @GetMapping( "/resultado" )
    public ResponseEntity<?> getResultado() 
    {
        ResultadoVotacao resultado = service.getResultadoVotacao();
        
        if ( resultado != null ) 
        {
            return ResponseEntity.ok( resultado );
        }
        
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping( "/votos/hoje" )
    public ResponseEntity<List<Voto>> getVotosHoje() 
    {
        return ResponseEntity.ok( service.findVotosByData( LocalDate.now() ) );
    }
} 