package br.com.alura.orcamentoapi.exception.handler;

import br.com.alura.orcamentoapi.exception.IdNotFoundException;
import br.com.alura.orcamentoapi.exception.ExceptionDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(IdNotFoundException.class)
    private ResponseEntity<ExceptionDetails> handleIdNotFoundException(IdNotFoundException ex){
        return new ResponseEntity<>(
                ExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .title("Id Not Found Exception")
                        .message(ex.getMessage())
                        .build(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ExceptionDetails exceptionDetails = ExceptionDetails.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .title(ex.getClass().getName())
                .message(ex.getMessage())
                .build();

        return  new ResponseEntity<>(exceptionDetails, headers, status);
    }
}
