package br.com.passaregua.fechaconta.buss.imposto;

import java.math.BigDecimal;
import java.math.RoundingMode;

import br.com.passaregua.fechaconta.util.UtilString;

/**
 * Created by Anderson on 29/06/2017.
 */

public class ImpostoAvulso implements IImposto {

    private String nomeImposto;
    private BigDecimal bdValorImposto;

    public ImpostoAvulso(String nomeImposto, BigDecimal bdValorImposto) {
        this.nomeImposto = nomeImposto;
        this.bdValorImposto = bdValorImposto.setScale(2, RoundingMode.DOWN);
    }

    @Override
    public BigDecimal calculaImposto(BigDecimal bdValorCalcular) {
        return bdValorImposto;
    }

    @Override
    public String getNomeImposto() {
        return nomeImposto;
    }

    @Override
    public boolean isImpostoGeral() {
        return false;
    }

    @Override
    public String toString() {
        return "Imposto Avulso('" + nomeImposto + "', '" + UtilString.formataCasasDecimais(bdValorImposto) + "')";
    }
}
