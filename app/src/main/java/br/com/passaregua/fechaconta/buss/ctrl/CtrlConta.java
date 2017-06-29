package br.com.passaregua.fechaconta.buss.ctrl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import br.com.passaregua.fechaconta.buss.conta.Conta;
import br.com.passaregua.fechaconta.buss.imposto.IImposto;
import br.com.passaregua.fechaconta.buss.imposto.ImpostoAvulso;
import br.com.passaregua.fechaconta.buss.imposto.ImpostoPorPercentual;
import br.com.passaregua.fechaconta.buss.imposto.ImpostoValorFixo;
import br.com.passaregua.fechaconta.buss.itens.Item;
import br.com.passaregua.fechaconta.buss.itens.ItemDividido;
import br.com.passaregua.fechaconta.util.UtilString;

/**
 * Created by Anderson on 28/06/2017.
 */
public class CtrlConta {
    private static CtrlConta myInstance;

    private List<Item> lsItens;
    private List<Conta> lsContas;
    private List<IImposto> lsImpostos;

    private CtrlConta() {
        lsItens = new ArrayList<Item>();
        lsContas = new ArrayList<Conta>();
        lsImpostos = new ArrayList<IImposto>();
    }

    /**
     * @return Retorna uma instancia SINGLETON do controle das contas e itens do sistema.
     */
    public static CtrlConta getInstance() {
        if(myInstance == null)
            myInstance = new CtrlConta();

        return myInstance;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Criar Item DIVIDIDO
    public boolean fazerDivisaoJusta(Item item, Conta ... contas) {
        if(item == null || contas == null ) return false;
        BigDecimal bdTotNaoDiv = item.calculaTotalNaoDividido();
        if(bdTotNaoDiv.compareTo(BigDecimal.ZERO) == 0) return false;

        BigDecimal bdValor = bdTotNaoDiv.divide(new BigDecimal(contas.length), new MathContext(3, RoundingMode.UP ));

        System.out.print("Dividindo o ITEM('" + item.getNumItem() + "', '" + item.getDescricao() + "', '" + bdTotNaoDiv + "') " +
                "... Divisao('" + contas.length + "', '" + bdValor + "') ... ");

        for(Conta conta : contas) {
            new ItemDividido(item, conta, bdValor);
        }

        bdTotNaoDiv = item.calculaTotalNaoDividido();
        if(bdTotNaoDiv.compareTo(BigDecimal.ZERO) != 0) {
            System.out.print("A divisao JUSTA teve um RESTO !! => '" + bdTotNaoDiv.toString() + "' ... ");
            List<ItemDividido> lsItensDivididos = item.getLsItensDivididos();
            Conta conta = null;
            for(ItemDividido itDiv : lsItensDivididos) {
                if(bdTotNaoDiv.doubleValue() < 0) {
                    conta = getContaMaiorTotal(contas);
                    if(conta.hasItemDividido(itDiv) == false ) {
                        continue;
                    }

                    itDiv.subtractRemainder();
                    bdTotNaoDiv = bdTotNaoDiv.add(new BigDecimal(0.01).setScale(2, RoundingMode.HALF_UP));

                } else if(bdTotNaoDiv.doubleValue() > 0) {
                    conta = getContaMenorTotal(contas);
                    if(conta.hasItemDividido(itDiv) == false ) {
                        continue;
                    }

                    itDiv.addRemainder();
                    bdTotNaoDiv = bdTotNaoDiv.subtract( new BigDecimal(0.01).setScale(2, RoundingMode.HALF_UP));
                }

                if(bdTotNaoDiv.compareTo(BigDecimal.ZERO) == 0) {
                    break;
                }
            }
        }

        System.out.println("DIVIDIDO!!");


        return true;
    }

    private Conta getContaMaiorTotal(Conta[] contas) {
        Conta contaMaior = null;
        BigDecimal bdValMaior = null;
        for(Conta conta : contas){
            if(contaMaior == null) {
                contaMaior = conta;
                bdValMaior = conta.calculaTotal();

            } else {
                if(bdValMaior.compareTo(conta.calculaTotal()) < 0)  {
                    contaMaior = conta;
                    bdValMaior = conta.calculaTotal();
                }
            }
        }

        return contaMaior;
    }

    private Conta getContaMenorTotal(Conta[] contas) {
        Conta contaMenor = null;
        BigDecimal bdValMenor = null;
        for(Conta conta : contas){
            if(contaMenor == null) {
                contaMenor = conta;
                bdValMenor = conta.calculaTotal();

            } else {
                if(bdValMenor.compareTo(conta.calculaTotal()) > 0)  {
                    contaMenor = conta;
                    bdValMenor = conta.calculaTotal();
                }
            }
        }

        return contaMenor;
    }

    public boolean fazerDivisaoAvulsa(Item item, Conta conta, BigDecimal bdValor) {
        if(item == null || conta == null || bdValor == null ) return false;
        BigDecimal bdTotNaoDiv = item.calculaTotalNaoDividido();

        //O valor passado nao pode ultrapassar o valor NAO Dividido.
        if(bdValor.compareTo(bdTotNaoDiv) > 0) {
            return false;
        }

        new ItemDividido(item, conta, bdValor);
        return true;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Incluir IMPOSTO
    public void incluirImpostoPercentual(String nomeImposto, BigDecimal bdValorImposto) {
        addImposto( new ImpostoPorPercentual(nomeImposto, bdValorImposto) );
    }

    public void incluirImpostoValorFixo(String nomeImposto, BigDecimal bdValorFixo) {
        addImposto( new ImpostoValorFixo(nomeImposto, bdValorFixo) );
    }

    public void incluirImpostoAvulso(String nomeImposto, BigDecimal bdValorImposto, Conta ... contas) {
        IImposto imp = new ImpostoAvulso(nomeImposto, bdValorImposto);
        for(Conta conta : contas) {
            conta.addImposto(imp);
        }

        lsImpostos.add(imp);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Retorno dos totais
    public BigDecimal calculaTotalItens() {
        BigDecimal bdTotal = new BigDecimal(0);
        for(Item item : lsItens){
            bdTotal = bdTotal.add(item.calculaTotal());
        }

        return bdTotal;
    }

    public BigDecimal calculaTotalContas() {
        BigDecimal bdTotal = new BigDecimal(0);
        for(Conta conta : lsContas){
            bdTotal = bdTotal.add(conta.calculaTotal());
        }

        return bdTotal;
    }

    public BigDecimal calculaTotalImposto() {
        BigDecimal bdTotal = new BigDecimal(0);
        for(Conta conta : lsContas){
            bdTotal = bdTotal.add(conta.calculaTotalImpostos());
        }

        return bdTotal;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Limpando os dados do Controler
    public void limparDados() {
        for(Item item: lsItens){
            List<ItemDividido> lsItDiv = item.getLsItensDivididos();
            for(int i = lsItDiv.size() - 1; i >= 0; i--) {
                ItemDividido itDiv = lsItDiv.get(i);
                itDiv.limparItem(); //Vai retirando todos os itens divididos das listas de ITEM e CONTA
            }
        }
        lsItens.clear();

        //TODO: Limpar IMPOSTOS da CONTA e DO CONTROLER
        for(int i = lsImpostos.size() - 1; i >= 0; i--) {
            IImposto imp = lsImpostos.get(i);
            removeImposto(imp); //Vai retirando todos os impostos das CONTAS E DO CONTROLER
        }

        lsContas.clear();
    }

    // Inclusao e Remoção de Itens
    public boolean addItem(Item item) {
        return lsItens.add(item);
    }

    public boolean removeItem(Item item) {
        return lsItens.remove(item);
    }

    // Inclusao e Remoção de Impostos
    public boolean addImposto(IImposto imposto) {
        for(Conta conta: lsContas) { //Deve incluir o imposto em todas as contas !!
            if(imposto.isImpostoGeral() == true) { //So inclui os impostos gerais !!
                conta.addImposto(imposto);
            }
        }

        return lsImpostos.add(imposto);
    }

    public boolean removeImposto(IImposto imposto) {
        for(Conta conta: lsContas){
            conta.removeImposto(imposto); //Deve remover o imposto em todas as contas !!
        }

        return lsImpostos.remove(imposto);
    }

    // Inclusao e Remoção de Contas
    public boolean addConta(Conta conta) {
        for(IImposto imposto : lsImpostos) {
            conta.addImposto(imposto);  //Deve incluir na conta, os impostos que existem !!
        }

        return lsContas.add(conta);
    }

    public boolean removeConta(Conta conta) {
        conta.limpaDados();  //Deve limpar a Conta antes de remover a CONTA.
        return lsContas.remove(conta);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                  GETTERS AND SETTERS

    /**
     * @return Retorna a Lista de ITENS incluidas no controle
     */
    public List<Item> getItens() {
        return lsItens;
    }

    /**
     * @return Retorna a lista de CONTAS incluidas nesse controle
     */
    public List<Conta> getContas() {
        return lsContas;
    }

    /**
     * @return Retorna a lista de CONTAS incluidas nesse controle
     */
    public List<IImposto> getImpostos() {
        return lsImpostos;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public String toString() {
        String out = "CtrlConta: \r\n" +
                "------------------------\r\n" +
                "ITENS\r\n" +
                "------------------------\r\n";
        for(Item item : lsItens){
            out += "* " + item.toString();
            out += "\r\n";
        }

        out += "\r\n" +
                "------------------------\r\n" +
                "CONTAS\r\n" +
                "------------------------\r\n";
        for(Conta conta : lsContas){
            out += "* " + conta.toString();
            out+= "\r\n\r\n";
        }

        out+= "------------------------\r\n" +
                "TOTAIS\r\n" +
                "------------------------\r\n" +
                "|TOTAL ITENS    => " + String.format("%8s", UtilString.formataCasasDecimais(calculaTotalItens()) ) + "\r\n" +
                "|TOTAL IMPOSTO  => " + String.format("%8s", UtilString.formataCasasDecimais(calculaTotalImposto()) ) + "\r\n" +
                "|TOTAL CONTAS   => " + String.format("%8s", UtilString.formataCasasDecimais(calculaTotalContas()) ) + "\r\n";

        return out;
    }
}
