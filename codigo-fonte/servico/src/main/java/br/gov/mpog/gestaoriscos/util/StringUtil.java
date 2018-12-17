package br.gov.mpog.gestaoriscos.util;

import java.text.Normalizer;
import java.util.Objects;


public class StringUtil {

    public static String removerAcento(String texto) {
        if (texto != null) {
            String string = Normalizer.normalize(texto, Normalizer.Form.NFD);
            return string.replaceAll("[^\\p{ASCII}]", "");
        } else {
            return null;
        }

    }

    public static String removerMascaraCpf(String cpfMascarado) {
        Objects.requireNonNull(cpfMascarado, "O CPF não pode ser nulo.");
        return cpfMascarado.trim().replaceAll("\\D", "");
    }

    private StringUtil() {
        throw new IllegalAccessError("Classe Utiliataria");
    }
}