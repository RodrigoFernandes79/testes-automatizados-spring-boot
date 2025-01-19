package br.com.rodrigo.rest_with_spring_boot.exception.handle;

import br.com.rodrigo.rest_with_spring_boot.exception.EmailJaEncontradoException;
import br.com.rodrigo.rest_with_spring_boot.exception.IdNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IdNotFoundException.class)
    public ResponseEntity<ResponseError> unsupportedMathOperation(IdNotFoundException e, WebRequest webRequest) {

        ResponseError error = new ResponseError(
                new Date(),
                e.getMessage(),
                webRequest.getDescription(false)  //passa o endpoint mostrando o erro "uri=/soma/5.1/q"
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(EmailJaEncontradoException.class)
    public ResponseEntity<ResponseError> emailJaEncontradoException(EmailJaEncontradoException e, WebRequest webRequest) {
        ResponseError error = new ResponseError(
                new Date(),
                e.getMessage(),
                webRequest.getDescription(false)
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);

    }

    private record ResponseError(
            Date timestamp,
            String message,
            String uriErro

    ) {
    }
}
