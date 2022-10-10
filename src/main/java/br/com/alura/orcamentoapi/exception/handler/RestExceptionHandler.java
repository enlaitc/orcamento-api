package br.com.alura.orcamentoapi.exception.handler;

import br.com.alura.orcamentoapi.exception.ExceptionDetails;
import br.com.alura.orcamentoapi.exception.InvalidTokenException;
import io.jsonwebtoken.SignatureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.time.DateTimeException;
import java.time.LocalDateTime;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    private ResponseEntity<ExceptionDetails> handleEntityNotFound(EntityNotFoundException ex, WebRequest request){
        return new ResponseEntity<>(
                ExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .title("Entity not found")
                        .message(ex.getMessage())
                        .build(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ExceptionDetails exceptionDetails = ExceptionDetails.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .title(ex.getClass().getSimpleName())
                .message(ex.getMessage())
                .build();

        return  new ResponseEntity<>(exceptionDetails, headers, status);
    }

    @ExceptionHandler({DataIntegrityViolationException.class, DateTimeException.class})
    protected ResponseEntity<ExceptionDetails> handleDataIntegrityViolationException(
            RuntimeException  ex, WebRequest request) {

        return new ResponseEntity<>(
                ExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .title(ex.getClass().getSimpleName())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .message(ex.getMessage())
                        .build(),HttpStatus.BAD_REQUEST);
    }

}
