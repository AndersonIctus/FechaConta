package br.com.passaregua.fechaconta.buss.conta;

import java.math.BigDecimal;
import java.util.List;

import br.com.passaregua.fechaconta.buss.itens.ItemDividido;

/**
 * Dependendo do IMPOSTO, um calculo diferente deve ser realizado !
 * Created by Anderson on 28/06/2017.
 */
public class Imposto {
    //Calcula o imposto sobre os itens !!
    public BigDecimal calculaImposto(List<ItemDividido> itens) {
        return new BigDecimal(0);
    }
}
