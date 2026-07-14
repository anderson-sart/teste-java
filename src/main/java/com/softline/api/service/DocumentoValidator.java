package com.softline.api.service;

public class DocumentoValidator {

    public static boolean isValid(String doc) {
        doc = doc.replaceAll("\\D", "");
        return switch (doc.length()) {
            case 11 -> validarCPF(doc);
            case 14 -> validarCNPJ(doc);
            default -> false;
        };
    }

    private static boolean validarCPF(String cpf) {
        if (cpf.matches("(\\d)\\1{10}")) return false;
        for (int t = 9; t < 11; t++) {
            int d = 0;
            for (int c = 0; c < t; c++) d += (cpf.charAt(c) - '0') * ((t + 1) - c);
            d = ((10 * d) % 11) % 10;
            if ((cpf.charAt(t) - '0') != d) return false;
        }
        return true;
    }

    private static boolean validarCNPJ(String cnpj) {
        if (cnpj.matches("(\\d)\\1{13}")) return false;
        int[] b = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        for (int t = 12; t < 14; t++) {
            int d = 0;
            for (int c = 0; c < t; c++) d += (cnpj.charAt(c) - '0') * b[c + (13 - t)];
            d = (d % 11 < 2) ? 0 : 11 - (d % 11);
            if ((cnpj.charAt(t) - '0') != d) return false;
        }
        return true;
    }
}
