package br.com.dbserver.votacao.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.dbserver.votacao.model.ResultadoVotacao;

@Repository
public interface ResultadoVotacaoRepository extends JpaRepository<ResultadoVotacao, Long> 
{
    Optional<ResultadoVotacao> findByData( LocalDate data );
} 