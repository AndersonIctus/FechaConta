package br.com.passaregua.fechaconta.buss.imposto;

import java.math.BigDecimal;
import java.util.List;

import br.com.passaregua.fechaconta.buss.itens.ItemDividido;

/**
 * Created by Anderson on 29/06/2017.
 */
public interface IImposto {
    public BigDecimal calculaImposto(BigDecimal bdValorCalcular);
    public String getNomeImposto();
    public boolean isImpostoGeral();
}
