package br.com.dbserver.votacao.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler 
{
    @ExceptionHandler( ResourceNotFoundException.class )
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException( ResourceNotFoundException ex )
    {
        ErrorResponse error = new ErrorResponse( HttpStatus.NOT_FOUND.value(), ex.getMessage() );

        return ResponseEntity.status( HttpStatus.NOT_FOUND ).body( error );
    }

    @ExceptionHandler( VotacaoException.class )
    public ResponseEntity<ErrorResponse> handleVotacaoException( VotacaoException ex )
    {
        ErrorResponse error = new ErrorResponse( HttpStatus.BAD_REQUEST.value(), ex.getMessage() );
    
        return ResponseEntity.status( HttpStatus.BAD_REQUEST ).body( error );
    }

    @ExceptionHandler( Exception.class )
    public ResponseEntity<ErrorResponse> handleGenericException( Exception ex )
    {
        ErrorResponse error = new ErrorResponse( HttpStatus.INTERNAL_SERVER_ERROR.value(), "Erro interno do servidor" );

        return ResponseEntity.status( HttpStatus.INTERNAL_SERVER_ERROR ).body( error );
    }
} 