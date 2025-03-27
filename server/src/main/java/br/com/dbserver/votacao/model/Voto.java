package br.com.dbserver.votacao.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Voto 
{
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;
    
    @ManyToOne
    @JoinColumn( name = "profissional_id" )
    private Profissional profissional;
    
    @ManyToOne
    @JoinColumn( name = "restaurante_id" )
    private Restaurante restaurante;
    
    private LocalDate data;
} 