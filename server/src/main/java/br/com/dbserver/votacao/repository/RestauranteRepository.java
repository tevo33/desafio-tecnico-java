package br.com.dbserver.votacao.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.dbserver.votacao.model.Restaurante;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long> 
{
    @Query( "  SELECT r FROM Restaurante r WHERE r.id NOT IN " +
            "( SELECT rv.restaurante.id FROM ResultadoVotacao rv WHERE rv.data BETWEEN ?1 AND ?2)" )
 
    List<Restaurante> findRestaurantesNaoEscolhidosNaSemana( LocalDate inicioSemana, LocalDate fimSemana );
} 