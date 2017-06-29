package br.com.passaregua.fechaconta.buss.itens;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import br.com.passaregua.fechaconta.util.UtilString;

/**
 * Created by Anderson on 28/06/2017.
 */
public class Item {
    private int numItem;
    private String descricao;
    private BigDecimal qtdTotal;
    private BigDecimal vlrUnitario;
    private List<ItemDividido> lsItensDivididos;

    /**
     * @param numItem
     * @param descricao
     * @param qtdTotal
     * @param vlrUnitario
     */
    public Item(int numItem, String descricao, BigDecimal qtdTotal, BigDecimal vlrUnitario) {
        this.numItem = numItem;
        this.descricao = descricao;
        this.qtdTotal = qtdTotal;
        this.vlrUnitario = vlrUnitario;
        lsItensDivididos = new ArrayList<ItemDividido>();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Inclusao e Remoção de Itens
    public boolean addItemDividido(ItemDividido itemDividido){
        return lsItensDivididos.add(itemDividido);
    }

    public boolean removeItemDividido(ItemDividido itemDividido){
        return lsItensDivididos.remove(itemDividido);
    }

    // Calculo de totais
    public BigDecimal calculaTotal() {
        return qtdTotal.multiply(vlrUnitario).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal calculaTotalDividido() {
        BigDecimal bdAux = new BigDecimal(0);

        for(ItemDividido itDiv : lsItensDivididos) {
            bdAux = bdAux.add( itDiv.calculaTotal() );
        }

        return bdAux.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal calculaTotalNaoDividido() {
        return calculaTotal().subtract( calculaTotalDividido() ).setScale(2, RoundingMode.HALF_UP);
    }

    public int getStatus() {
        BigDecimal bdTot = calculaTotal();
        BigDecimal bdTotNaoDiv = bdTot.subtract( calculaTotalDividido() );

        if( bdTotNaoDiv.compareTo(bdTot) == 0 ) return 2;
        else if( bdTotNaoDiv.doubleValue() > 0.00) return 1;
        else return 0;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    //                                  GETTERS AND SETTERS
    public int getNumItem() {
        return numItem;
    }

    public void setNumItem(int numItem) {
        this.numItem = numItem;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getQtdTotal() {
        return qtdTotal;
    }

    public void setQtdTotal(BigDecimal qtdTotal) {
        this.qtdTotal = qtdTotal;
    }

    public BigDecimal getVlrUnitario() {
        return vlrUnitario;
    }

    public void setVlrUnitario(BigDecimal vlrUnitario) {
        this.vlrUnitario = vlrUnitario;
    }

    public List<ItemDividido> getLsItensDivididos() {
        return lsItensDivididos;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    public String toString() {
        String out = "Item ['" +
                numItem + "', '" +
                descricao + "', '" +
                UtilString.formataCasasDecimais(qtdTotal, 3) + "', '" +
                UtilString.formataCasasDecimais(vlrUnitario) + "', '" +
                UtilString.formataCasasDecimais(calculaTotal()) + "']";

        return out;
    }

    @Override
    public int hashCode() {
        return numItem + descricao.hashCode();
    }
}
