package br.com.passaregua.fechaconta.util;

import java.math.BigDecimal;

/**
 * Created by Anderson on 28/06/2017.
 */
public class UtilString {
    private UtilString() {}

    public static String zerosLeft(int valor, int numZerosMax) {
        String strValor = valor + "";
        while(strValor.length() < numZerosMax) {
            strValor = "0" + strValor;
        }

        return strValor;
    }

    public static String formataCasasDecimais(BigDecimal bgDecimal, int numCasas) {
        return String.format("%." + numCasas + "f", bgDecimal);
    }

    public static String formataCasasDecimais(BigDecimal bgDecimal) {
        return String.format("%.2f", bgDecimal);
    }
}
