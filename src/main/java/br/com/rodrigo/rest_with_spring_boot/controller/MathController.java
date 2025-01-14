package br.com.rodrigo.rest_with_spring_boot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MathController {

    @GetMapping("/soma/{primeiroNumero}/{segundoNumero}")
    public Double soma(@PathVariable String primeiroNumero, @PathVariable String segundoNumero) {
        if (!isNumerico(primeiroNumero) || !isNumerico(segundoNumero)) {
            throw new RuntimeException();
        }
        return converterParaDouble(primeiroNumero) + converterParaDouble(segundoNumero);
    }

    private Double converterParaDouble(String numeroString) {
        if (numeroString == null) {
            return 0D;
        }
        // ao digitar 4,5 ele substitui por 4.5
        String numero = numeroString.replace(",", ".");
        return Double.parseDouble(numero);
    }

    private boolean isNumerico(String numeroString) {
        if (numeroString == null) {
            return false;
        }
        String numero = numeroString.replaceAll(",", ".");
        //verifica se ele bate com o valor numerico (o regex abaixo verifica se o valor é numerico)
        return numero.matches("^-?\\d+(\\.\\d+)?$");
    }
}
