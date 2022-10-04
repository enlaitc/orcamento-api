package br.com.alura.orcamentoapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IdNotFoundException extends RuntimeException {
    public IdNotFoundException(String msg) {
        super(msg);
    }


}
