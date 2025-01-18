package br.com.rodrigo.rest_with_spring_boot.exception;

public class ListaDePessoasNaoEncontradaException extends RuntimeException {
    public ListaDePessoasNaoEncontradaException(String message) {
        super(message);
    }
}
