package br.com.alura.orcamentoapi.exception;

public class IdNotFoundException extends IllegalArgumentException {
    public IdNotFoundException(String msg) {
        super(msg);
    }


}
