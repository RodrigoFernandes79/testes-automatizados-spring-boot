package br.com.rodrigo.rest_with_spring_boot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MathController {

    @GetMapping("/soma/{primeiroNumero}/{segundoNumero}")
    public Double soma(@PathVariable String primeiroNumero, @PathVariable String segundoNumero ) {

        return 1D;
    }
}
