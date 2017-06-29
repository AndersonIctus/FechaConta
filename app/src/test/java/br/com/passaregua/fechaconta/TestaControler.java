package br.com.passaregua.fechaconta;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import br.com.passaregua.fechaconta.buss.conta.Conta;
import br.com.passaregua.fechaconta.buss.conta.Pagante;
import br.com.passaregua.fechaconta.buss.ctrl.CtrlConta;
import br.com.passaregua.fechaconta.buss.itens.Item;

/**
 * Created by Anderson on 28/06/2017.
 */

public class TestaControler {

    @Test
    public void testaControler() throws Exception {
        CtrlConta ctrl = CtrlConta.getInstance();

        incluirItens(ctrl);
        incluirContas(ctrl);
        incluirImpostos(ctrl);

//        incluirImpostos(ctrl);
//        fazTeste_1(ctrl);
//        ctrl.limparDados();
//
//        incluirItens(ctrl);
//        incluirContas(ctrl);

        fazTeste_2(ctrl);
        ctrl.limparDados();
    }



    private void fazTeste_1(CtrlConta ctrl) {
        System.out.println("================== TESTE 01 ==================");

        List<Item> lsItem = ctrl.getItens();
        List<Conta> lsContas = ctrl.getContas();
        Conta[] contas = new Conta[lsContas.size()];
        for(int i = 0 ; i < lsContas.size(); i++){
            contas[i] = lsContas.get(i);
        }

        //Dividindo itens
        for(int i = 0; i < lsItem.size(); i++ ) {
            ctrl.fazerDivisaoJusta(lsItem.get(i), contas);
        }

        System.out.println(ctrl.toString());
        System.out.println("==============================================");
    }

    private void fazTeste_2(CtrlConta ctrl) {
        System.out.println("================== TESTE 02 ==================");
        ctrl.incluirImpostoValorFixo("Couver", new BigDecimal(2.5) );

        ctrl.addConta( new Conta(new Pagante("Silmara Arlinda")) );

        ctrl.addItem( new Item(4, "Lasanha", new BigDecimal(1), new BigDecimal(15.9)) );
        ctrl.addItem( new Item(5, "Picanha Nacional", new BigDecimal(0.600), new BigDecimal(29.9)) );

        //Contas ....
        List<Conta> lsContas = ctrl.getContas();
        Conta[] contas = new Conta[lsContas.size()];
        for(int i = 0 ; i < lsContas.size(); i++){
            contas[i] = lsContas.get(i);
        }

        //Itens ....
        List<Item> lsIten = ctrl.getItens();
        Item[] itens = new Item[lsIten.size()];
        for(int i = 0 ; i < lsIten.size(); i++){
            itens[i] = lsIten.get(i);
        }

        //Fazendo Divisoes ...
        ctrl.fazerDivisaoJusta(itens[0], contas);
        ctrl.fazerDivisaoJusta(itens[1], contas[0], contas[2], contas[3] );
        ctrl.fazerDivisaoJusta(itens[2], contas[0], contas[1]);
        ctrl.fazerDivisaoJusta(itens[3], contas[1], contas[2]);
        ctrl.fazerDivisaoJusta(itens[4], contas[0], contas[1], contas[3]);

        ctrl.incluirImpostoAvulso("Estacionamento", new BigDecimal(3.00), contas[0], contas[3]);

        System.out.println(ctrl.toString());
        System.out.println("==============================================");
    }

    private void incluirImpostos(CtrlConta ctrl) {
        ctrl.incluirImpostoPercentual("10% Garçom", new BigDecimal(10.00));
    }

    private void incluirContas(CtrlConta ctrl) {
        ctrl.addConta( new Conta(new Pagante("Anderson Cunha")) );
        ctrl.addConta( new Conta(new Pagante("Joao Felipe")) );
        ctrl.addConta( new Conta(new Pagante("Mariana Lopes")) );
    }

    private void incluirItens(CtrlConta ctrl) {
        ctrl.addItem( new Item(1, "Coca Cola 1ltr", new BigDecimal(4), new BigDecimal(2.5)) );
        ctrl.addItem( new Item(2, "Churrasco Carne", new BigDecimal(5), new BigDecimal(1.75)) );
        ctrl.addItem( new Item(3, "Picole Natural", new BigDecimal(2), new BigDecimal(4.3)) );
    }
}
