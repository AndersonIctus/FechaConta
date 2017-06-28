package br.com.passaregua.fechaconta.buss.ctrl;

import java.util.ArrayList;
import java.util.List;

import br.com.passaregua.fechaconta.buss.conta.Conta;
import br.com.passaregua.fechaconta.buss.itens.Item;

/**
 * Created by Anderson on 28/06/2017.
 */
public class CtrlConta {
    private static CtrlConta myInstance;

    private List<Item> lsItens;
    private List<Conta> lsContas;

    private CtrlConta() {
        lsItens = new ArrayList<Item>();
        lsContas = new ArrayList<Conta>();
    }

    static CtrlConta getInstance() {
        if(myInstance == null)
            myInstance = new CtrlConta();

        return myInstance;
    }

    ////////////////////////////////////////////////////////////////
    // Inclusao e Remoção de Itens
    public boolean addItem(Item item) {
        return lsItens.add(item);
    }

    public boolean removeItem(Item item) {
        return lsItens.remove(item);
    }

    // Inclusao e Remoção de Contas
    public boolean addConta(Conta conta) {
        return lsContas.add(conta);
    }

    public boolean removeConta(Conta conta) {
        return lsContas.remove(conta);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    //                                  GETTERS AND SETTERS
    public List<Item> getLsItens() {
        return lsItens;
    }

    public List<Conta> getLsContas() {
        return lsContas;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////
}
