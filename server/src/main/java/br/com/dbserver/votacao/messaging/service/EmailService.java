package br.com.dbserver.votacao.messaging.service;

import java.time.format.DateTimeFormatter;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import br.com.dbserver.votacao.messaging.dto.VotoMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService
{
    private final JavaMailSender mailSender;
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern( "dd/MM/yyyy" );

    public void enviarEmailConfirmacaoVoto( VotoMessage votoMessage ) 
    {
        try 
        {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(votoMessage.getEmailProfissional());
            message.setSubject( "Confirmação de Voto - Sistema de Votação de Restaurantes" );
            
            String corpo = String.format(
                           "Olá %s,\n\n" +
                           "Seu voto para o restaurante %s foi registrado com sucesso em %s.\n\n" +
                           "Obrigado por participar da votação!\n\n" +
                           "Atenciosamente,\n" +
                           "Equipe DBServer",
                           votoMessage.getNomeProfissional(),
                           votoMessage.getNomeRestaurante(),
                           votoMessage.getDataVoto().format( DATE_FORMATTER )
            );
            
            message.setText( corpo );
            
            mailSender.send( message );
            
            log.info( "Email de confirmação enviado para {}", votoMessage.getEmailProfissional() );
        } 
        
        catch ( Exception e ) 
        {
            log.error( "Erro ao enviar email: {}", e.getMessage(), e );
            
            throw e;
        }
    }
} 