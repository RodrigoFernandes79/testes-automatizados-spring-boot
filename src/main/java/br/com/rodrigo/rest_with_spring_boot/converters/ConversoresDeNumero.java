package br.com.rodrigo.rest_with_spring_boot.converters;

import org.springframework.stereotype.Component;

@Component
public class ConversoresDeNumero {
    public Double converterParaDouble(String numeroString) {
        if (numeroString == null) {
            return 0D;
        }
        // ao digitar 4,5 ele substitui por 4.5
        String numero = numeroString.replace(",", ".");
        return Double.parseDouble(numero);
    }

    public boolean isNumerico(String numeroString) {
        if (numeroString == null) {
            return false;
        }
        String numero = numeroString.replaceAll(",", ".");
        //verifica se ele bate com o valor numerico (o regex abaixo verifica se o valor é numerico)
        return numero.matches("^-?\\d+(\\.\\d+)?$");
    }
}
