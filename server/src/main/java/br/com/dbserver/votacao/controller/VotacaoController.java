package br.com.dbserver.votacao.controller;

import java.time.LocalDate;
import java.util.List;

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
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping( "/api/votacao" )
@CrossOrigin( origins = "*" )
@RequiredArgsConstructor
public class VotacaoController 
{
    private final VotacaoService service;
    
    @PostMapping( "/votar" )
    public ResponseEntity<Voto> votar( @RequestBody Voto voto )
    {
        return ResponseEntity.status( HttpStatus.CREATED ).body( service.votar( voto ) );
    }
    
    @GetMapping( "/resultado" )
    public ResponseEntity<ResultadoVotacao> getResultado() 
    {
        return ResponseEntity.ok( service.getResultadoVotacao() );
    }
    
    @GetMapping( "/votos/hoje" )
    public ResponseEntity<List<Voto>> getVotosHoje() 
    {
        return ResponseEntity.ok( service.findVotosByData( LocalDate.now() ) );
    }
} 