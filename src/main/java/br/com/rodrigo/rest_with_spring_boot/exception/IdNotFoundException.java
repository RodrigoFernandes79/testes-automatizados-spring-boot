package br.com.rodrigo.rest_with_spring_boot.exception;

public class UnsupportedMathOperation extends RuntimeException {
    public UnsupportedMathOperation(String message) {
        super(message);
    }
}
