package br.com.passaregua.fechaconta.buss.conta;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.com.passaregua.fechaconta.buss.imposto.IImposto;
import br.com.passaregua.fechaconta.buss.itens.ItemDividido;
import br.com.passaregua.fechaconta.util.UtilString;

/**
 * Created by Anderson on 28/06/2017.
 */
public class Conta {
    private Pagante pagante;
    private List<ItemDividido> lsItens;
    private List<IImposto> lsImposto;

    public Conta(Pagante pagante) {
        this.pagante = pagante;
        lsItens = new ArrayList<ItemDividido>();
        lsImposto = new ArrayList<IImposto>();
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

    public boolean hasItemDividido(ItemDividido itemDividido) {
        return lsItens.contains(itemDividido);
    }

    // Inclusao e Remoção de Imposto
    public boolean addImposto(IImposto imposto) {
        return lsImposto.add(imposto);
    }

    public boolean removeImposto(IImposto imposto) {
        return lsImposto.remove(imposto);
    }

    public boolean hasImposto(IImposto imposto) {
        return lsImposto.contains(imposto);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Calculo de Total
    public BigDecimal calculaTotal() {
        BigDecimal bdAux = new BigDecimal(0);

        bdAux = bdAux.add(calculaTotalItens());
        bdAux = bdAux.add(calculaTotalImpostos());

        return bdAux;
    }

    public BigDecimal calculaTotalItens() {
        BigDecimal bdAux = new BigDecimal(0);

        for(ItemDividido item:lsItens) {
            bdAux = bdAux.add( item.calculaTotal() );
        }

        return bdAux;
    }

    public BigDecimal calculaTotalImpostos() {
        BigDecimal bdAux = new BigDecimal(0);
        BigDecimal bdTotalItens = calculaTotalItens();

        for(IImposto imposto : lsImposto) {
            bdAux = bdAux.add(imposto.calculaImposto(bdTotalItens));
        }

        return bdAux;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Limpa os dados dessa conta
    public void limpaDados() {
        for(int i = lsItens.size() - 1; i >= 0; i--) {
            ItemDividido itDiv = lsItens.get(i);
            itDiv.limparItem(); //Vai retirando todos os itens divididos das listas de ITEM e CONTA
        }

        lsItens.clear();
        lsImposto.clear();
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

    public List<IImposto> getLsImposto() {
        return lsImposto;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    public String toString() {
        String out = "Conta [" + pagante.toString() + "] \r\n" +
                "  - Itens Divididos\r\n";
        for(ItemDividido item : lsItens){
            out += "    -> " + item.toString();
            out += "\r\n";
        }

        out += "  - IMPOSTOS\r\n";
        BigDecimal bdTotalItens = calculaTotalItens();
        for(IImposto imp : lsImposto) {
            out += "    -> " + imp.toString() + " ['" + imp.calculaImposto(bdTotalItens) + "']";
            out += "\r\n";
        }

        out += "## S U B   T O T A L -> " + String.format("%8s", UtilString.formataCasasDecimais(bdTotalItens) ) + "\r\n";
        out += "## I M P O S T O     -> " + String.format("%8s", UtilString.formataCasasDecimais(calculaTotalImpostos()) ) + "\r\n";
        out += "## T O T A L         -> " + String.format("%8s", UtilString.formataCasasDecimais(calculaTotal()) );

        return out;
    }

    @Override
    public int hashCode() {
        return pagante.hashCode() + lsItens.hashCode() + lsImposto.hashCode();
    }
}
