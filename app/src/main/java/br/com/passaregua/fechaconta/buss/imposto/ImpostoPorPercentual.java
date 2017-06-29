package br.com.passaregua.fechaconta.buss.imposto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import br.com.passaregua.fechaconta.buss.itens.ItemDividido;
import br.com.passaregua.fechaconta.util.UtilString;

/**
 * Dependendo do IMPOSTO, um calculo diferente deve ser realizado !
 * Created by Anderson on 28/06/2017.
 */
public class ImpostoPorPercentual implements IImposto {
    private String nomeImposto;
    private BigDecimal bdPercentual;

    public ImpostoPorPercentual(String nomeImposto, BigDecimal bdPercentual) {
        this.nomeImposto = nomeImposto;
        this.bdPercentual = bdPercentual;
    }

    @Override
    public BigDecimal calculaImposto(BigDecimal bdValorTotal) {
        BigDecimal bdTotal = bdValorTotal;

        bdTotal = bdTotal.multiply(bdPercentual).divide(new BigDecimal(100)).setScale(2, RoundingMode.DOWN);

        return bdTotal;
    }

    @Override
    public String getNomeImposto() {
        return nomeImposto;
    }

    @Override
    public boolean isImpostoGeral() {
        return true;
    }

    @Override
    public String toString() {
        return "ImpostoPorPercentual('" + nomeImposto + "', '" + UtilString.formataCasasDecimais(bdPercentual) + "%')";
    }
}
