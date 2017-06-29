package br.com.passaregua.fechaconta.buss.imposto;

import java.math.BigDecimal;
import java.math.RoundingMode;

import br.com.passaregua.fechaconta.util.UtilString;

/**
 * Created by Anderson on 29/06/2017.
 */

public class ImpostoValorFixo implements IImposto {
    private String nomeImposto;
    private BigDecimal bdValorFixo;

    public ImpostoValorFixo(String nomeImposto, BigDecimal bdValorFixo) {
        this.nomeImposto = nomeImposto;
        this.bdValorFixo = bdValorFixo;
    }

    @Override
    public BigDecimal calculaImposto(BigDecimal bdValorTotal) {
        return bdValorFixo;
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
        return "Imposto de ValorFixo('" + nomeImposto + "', '" + UtilString.formataCasasDecimais(bdValorFixo) + "')";
    }
}
