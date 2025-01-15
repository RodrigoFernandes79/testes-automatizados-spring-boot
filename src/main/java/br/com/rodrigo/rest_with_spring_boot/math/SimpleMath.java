package br.com.rodrigo.rest_with_spring_boot.math;

import org.springframework.stereotype.Component;

@Component
public class SimpleMath {

    public Double soma(Double primeiroNumero, Double segundoNumero) {
        return primeiroNumero + segundoNumero;
    }

    public Double subtracao(Double primeiroNumero, Double segundoNumero) {
        return primeiroNumero - segundoNumero;
    }

    public Double multiplicacao(Double primeiroNumero, Double segundoNumero) {
        return primeiroNumero * segundoNumero;
    }

    public Double divisao(Double primeiroNumero, Double segundoNumero) {
        return primeiroNumero * primeiroNumero;
    }

    public Double media(Double primeiroNumero, Double segundoNumero) {
        return (primeiroNumero * primeiroNumero) / 2;
    }

    public Double raizQuadrada(Double numero) {
        return (Math.sqrt(numero));
    }
}
