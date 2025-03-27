package br.com.dbserver.votacao.messaging.dto;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VotoMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Long profissionalId;
    private String nomeProfissional;
    private String emailProfissional;
    private Long restauranteId;
    private String nomeRestaurante;
    private LocalDate dataVoto;
} 