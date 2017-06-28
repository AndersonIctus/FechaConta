package br.com.passaregua.fechaconta.buss.itens;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anderson on 28/06/2017.
 */
public class Item {
    private String nome;
    private BigDecimal qtdTotal;
    private BigDecimal vlrUnitario;
    private List<ItemDividido> lsItensDivididos;

    public Item(String nome, BigDecimal qtdTotal, BigDecimal vlrUnitario) {
        this.nome = nome;
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
        return qtdTotal.multiply(vlrUnitario);
    }

    public BigDecimal calculaTotalDividido() {
        BigDecimal bdAux = new BigDecimal(0);

        for(ItemDividido itDiv : lsItensDivididos) {
            bdAux = bdAux.add( itDiv.calculaTotal() );
        }

        return bdAux;
    }

    public BigDecimal calculaTotalNaoDividido() {
        return calculaTotal().subtract( calculaTotalDividido() );
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
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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
}
