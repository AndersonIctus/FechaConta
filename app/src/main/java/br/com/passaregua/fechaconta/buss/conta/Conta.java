package br.com.passaregua.fechaconta.buss.conta;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.com.passaregua.fechaconta.buss.itens.ItemDividido;

/**
 * Created by Anderson on 28/06/2017.
 */
public class Conta {
    private Pagante pagante;
    private List<ItemDividido> lsItens;
    private List<Imposto> lsImposto;

    //TODO: RATEIO DO TOTAL !!
    // -  Se sobrar, retira dos maiores
    // -  Se faltar, poe nos maiores

    public Conta(Pagante pagante) {
        this.pagante = pagante;
        lsItens = new ArrayList<ItemDividido>();
        lsImposto = new ArrayList<Imposto>();
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Inclusao e Remoção de Itens
    public boolean addItemDividido(ItemDividido itemDividido) {
        return lsItens.add(itemDividido);
    }

    public boolean removeItemDividido(ItemDividido itemDividido) {
        return lsItens.remove(itemDividido);
    }
    // Inclusao e Remoção de Imposto
    public boolean addImposto(Imposto imposto) {
        return lsImposto.add(imposto);
    }

    public boolean removeImposto(Imposto imposto) {
        return lsImposto.remove(imposto);
    }

    // Calculo de total
    public BigDecimal calculaTotal() {
        BigDecimal bdAux = new BigDecimal(0);

        for(ItemDividido item:lsItens) {
            bdAux = bdAux.add( item.calculaTotal() );
        }

        for(Imposto imposto : lsImposto) {
            bdAux = bdAux.add(imposto.calculaImposto(lsItens));
        }

        return bdAux;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    //                                  GETTERS AND SETTERS
    public Pagante getPagante() {
        return pagante;
    }

    public void setPagante(Pagante pagante) {
        this.pagante = pagante;
    }

    public List<ItemDividido> getLsItens() {
        return lsItens;
    }

    public List<Imposto> getLsImposto() {
        return lsImposto;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////
}
