package br.com.dbserver.votacao.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.dbserver.votacao.model.Profissional;
import br.com.dbserver.votacao.model.Voto;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Long> 
{
    boolean existsByProfissionalAndData( Profissional profissional, LocalDate data );
    
    List<Voto> findByData( LocalDate data );
    
    @Query( "SELECT v.restaurante.id, COUNT(v) as count FROM Voto v WHERE v.data = ?1 GROUP BY v.restaurante.id ORDER BY count DESC" )
    
    List<Object[]> countVotosByRestauranteAndData( LocalDate data );
} 