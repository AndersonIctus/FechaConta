package br.com.passaregua.fechaconta.buss.itens;

import java.math.BigDecimal;
import br.com.passaregua.fechaconta.buss.conta.Conta;

/**
 * Created by Anderson on 28/06/2017.
 */
public class ItemDividido {
    public enum Regra {
        JUSTA,
        AVULSA,
        POR_QTD
    };

    private Item item;
    private Conta conta;
    private Regra regraDivisao;
    private BigDecimal vlrAuxRegra;

    public ItemDividido(Item item, Conta conta, Regra regraDivisao, BigDecimal vlrAuxRegra) {
        this.item = item;
        this.conta = conta;
        this.regraDivisao = regraDivisao;
        this.vlrAuxRegra = vlrAuxRegra;

        item.addItemDividido(this);
        conta.addItemDividido(this);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Calculo de total
    public BigDecimal calculaTotal() {
        switch(regraDivisao) {
            default:
            case JUSTA:   return item.calculaTotal().divide(vlrAuxRegra);
            case POR_QTD: return item.getVlrUnitario().multiply(vlrAuxRegra);
            case AVULSA:  return vlrAuxRegra;
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    //                                  GETTERS AND SETTERS
    public Item getItem() {
        return item;
    }

    public Conta getConta() {
        return conta;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////
}
