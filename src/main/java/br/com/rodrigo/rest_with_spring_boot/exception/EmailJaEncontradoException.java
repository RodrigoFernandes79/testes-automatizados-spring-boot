package br.com.rodrigo.rest_with_spring_boot.exception;

public class EmailJaEncontradoException extends RuntimeException {
    public EmailJaEncontradoException(String message) {
        super(message);
    }
}
