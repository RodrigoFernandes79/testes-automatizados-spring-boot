package br.com.rodrigo.rest_with_spring_boot.controller;

import br.com.rodrigo.rest_with_spring_boot.converters.ConversoresDeNumero;
import br.com.rodrigo.rest_with_spring_boot.exception.UnsupportedMathOperation;
import br.com.rodrigo.rest_with_spring_boot.math.SimpleMath;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MathController {

    private final ConversoresDeNumero conversoresDeNumero;
    private final SimpleMath math;

    public MathController(ConversoresDeNumero conversoresDeNumero, SimpleMath math) {
        this.conversoresDeNumero = conversoresDeNumero;
        this.math = math;
    }

    @GetMapping("/soma/{primeiroNumero}/{segundoNumero}")
    public Double soma(@PathVariable String primeiroNumero, @PathVariable String segundoNumero) {
        if (!conversoresDeNumero.isNumerico(primeiroNumero) || !conversoresDeNumero.isNumerico(segundoNumero)) {
            throw new UnsupportedMathOperation("Por favor, insira apenas valores numéricos");
        }

        return math.soma(
                conversoresDeNumero.converterParaDouble(primeiroNumero),
                conversoresDeNumero.converterParaDouble(segundoNumero)
        );
    }

    @GetMapping("/subtracao/{primeiroNumero}/{segundoNumero}")
    public Double subtracao(@PathVariable String primeiroNumero, @PathVariable String segundoNumero) {
        if (!conversoresDeNumero.isNumerico(primeiroNumero) || !conversoresDeNumero.isNumerico(segundoNumero)) {
            throw new UnsupportedMathOperation("Por favor, insira apenas valores numéricos");
        }

        return math.subtracao(
                conversoresDeNumero.converterParaDouble(primeiroNumero),
                conversoresDeNumero.converterParaDouble(segundoNumero)
        );
    }

    @GetMapping("/multiplicacao/{primeiroNumero}/{segundoNumero}")
    public Double multiplicacao(@PathVariable String primeiroNumero, @PathVariable String segundoNumero) {
        if (!conversoresDeNumero.isNumerico(primeiroNumero) || !conversoresDeNumero.isNumerico(segundoNumero)) {
            throw new UnsupportedMathOperation("Por favor, insira apenas valores numéricos");
        }

        return math.multiplicacao(
                conversoresDeNumero.converterParaDouble(primeiroNumero),
                conversoresDeNumero.converterParaDouble(segundoNumero)
        );
    }

    @GetMapping("/divisao/{primeiroNumero}/{segundoNumero}")
    public Double divisao(@PathVariable String primeiroNumero, @PathVariable String segundoNumero) {
        if (!conversoresDeNumero.isNumerico(primeiroNumero) || !conversoresDeNumero.isNumerico(segundoNumero)) {
            throw new UnsupportedMathOperation("Por favor, insira apenas valores numéricos");
        }

        return math.divisao(
                conversoresDeNumero.converterParaDouble(primeiroNumero),
                conversoresDeNumero.converterParaDouble(segundoNumero)
        );
    }

    @GetMapping("/media/{primeiroNumero}/{segundoNumero}")
    public Double media(@PathVariable String primeiroNumero, @PathVariable String segundoNumero) {
        if (!conversoresDeNumero.isNumerico(primeiroNumero) || !conversoresDeNumero.isNumerico(segundoNumero)) {
            throw new UnsupportedMathOperation("Por favor, insira apenas valores numéricos");
        }

        return math.media(
                conversoresDeNumero.converterParaDouble(primeiroNumero),
                conversoresDeNumero.converterParaDouble(segundoNumero)
        );
    }

    @GetMapping("/raiz_quadrada/{numero}")
    public Double raizQuadrada(@PathVariable String numero) {
        if (!conversoresDeNumero.isNumerico(numero)) {
            throw new UnsupportedMathOperation("Por favor, insira apenas valores numéricos");
        }

        return math.raizQuadrada(
                conversoresDeNumero.converterParaDouble(numero)
        );
    }


}
