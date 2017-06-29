package br.com.passaregua.fechaconta.buss.itens;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import br.com.passaregua.fechaconta.buss.conta.Conta;
import br.com.passaregua.fechaconta.util.UtilString;

/**
 * Created by Anderson on 28/06/2017.
 */
public class ItemDividido {
    private Item item;
    private Conta conta;
    private BigDecimal vlrAuxRegra;

    public ItemDividido(Item item, Conta conta, BigDecimal vlrAuxRegra) {
        this.item = item;
        this.conta = conta;
        this.vlrAuxRegra = vlrAuxRegra;

        item.addItemDividido(this);
        conta.addItemDividido(this);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // O valor Auxiliar jah eh o total para esse item dividido !
    public BigDecimal calculaTotal() {
        return vlrAuxRegra;
    }

    public void limparItem() {
        item.removeItemDividido(this);
        conta.removeItemDividido(this);
        item = null;
        conta = null;
    }

    public void addRemainder() {
        this.vlrAuxRegra = this.vlrAuxRegra.add(new BigDecimal(0.01).setScale(2, RoundingMode.HALF_UP));
    }

    public void subtractRemainder() {
        this.vlrAuxRegra = this.vlrAuxRegra.subtract(new BigDecimal(0.01).setScale(2, RoundingMode.HALF_UP));
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


    @Override
    public String toString() {
        String out = "ItemDividido( " +
                "Item('" + item.getNumItem() + "'), " +
                "Conta('" + conta.getPagante().getNome() + "'), " +
                "TOTAL -> '" + UtilString.formataCasasDecimais(vlrAuxRegra) + "' )";
        return out;
    }

    @Override
    public int hashCode() {
        return (2*item.getNumItem()) + (3* conta.hashCode());
    }
}
