package br.com.dbserver.votacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.dbserver.votacao.model.Profissional;

@Repository
public interface ProfissionalRepository extends JpaRepository<Profissional, Long> {}