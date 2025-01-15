package br.com.rodrigo.rest_with_spring_boot.controller;

import br.com.rodrigo.rest_with_spring_boot.exception.UnsupportedMathOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MathController {

    @GetMapping("/soma/{primeiroNumero}/{segundoNumero}")
    public Double soma(@PathVariable String primeiroNumero, @PathVariable String segundoNumero) {
        if (!isNumerico(primeiroNumero) || !isNumerico(segundoNumero)) {
            throw new UnsupportedMathOperation("Por favor, insira apenas valores numéricos");
        }

        return converterParaDouble(primeiroNumero) + converterParaDouble(segundoNumero);
    }

    @GetMapping("/subtracao/{primeiroNumero}/{segundoNumero}")
    public Double subtracao(@PathVariable String primeiroNumero, @PathVariable String segundoNumero) {
        if (!isNumerico(primeiroNumero) || !isNumerico(segundoNumero)) {
            throw new UnsupportedMathOperation("Por favor, insira apenas valores numéricos");
        }

        return converterParaDouble(primeiroNumero) - converterParaDouble(segundoNumero);
    }

    @GetMapping("/multiplicacao/{primeiroNumero}/{segundoNumero}")
    public Double multiplicacao(@PathVariable String primeiroNumero, @PathVariable String segundoNumero) {
        if (!isNumerico(primeiroNumero) || !isNumerico(segundoNumero)) {
            throw new UnsupportedMathOperation("Por favor, insira apenas valores numéricos");
        }

        return converterParaDouble(primeiroNumero) * converterParaDouble(segundoNumero);
    }

    @GetMapping("/divisao/{primeiroNumero}/{segundoNumero}")
    public Double divisao(@PathVariable String primeiroNumero, @PathVariable String segundoNumero) {
        if (!isNumerico(primeiroNumero) || !isNumerico(segundoNumero)) {
            throw new UnsupportedMathOperation("Por favor, insira apenas valores numéricos");
        }

        return converterParaDouble(primeiroNumero) * converterParaDouble(segundoNumero);
    }

    @GetMapping("/media/{primeiroNumero}/{segundoNumero}")
    public Double media(@PathVariable String primeiroNumero, @PathVariable String segundoNumero) {
        if (!isNumerico(primeiroNumero) || !isNumerico(segundoNumero)) {
            throw new UnsupportedMathOperation("Por favor, insira apenas valores numéricos");
        }

        return (converterParaDouble(primeiroNumero) * converterParaDouble(segundoNumero)) / 2;
    }

    @GetMapping("/raiz_quadrada/{numero}")
    public Double raizQuadrada(@PathVariable String numero) {
        if (!isNumerico(numero)) {
            throw new UnsupportedMathOperation("Por favor, insira apenas valores numéricos");
        }

        return (Math.sqrt(converterParaDouble(numero)));
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
