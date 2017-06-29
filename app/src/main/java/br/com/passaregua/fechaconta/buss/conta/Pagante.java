package br.com.passaregua.fechaconta.buss.conta;

/**
 *  A classe vai guardar os links para os usuários !!
 * Created by Anderson on 28/06/2017.
 */
public class Pagante {
    //TODO: Pesquisar Sobre como incluir dados dos contatos do telefone, e compartilhamento com WhatsApp
    String nome;

    public Pagante(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Pagante('" + nome + "')";
    }

    @Override
    public int hashCode() {
        if(nome == null || nome.equals("")) return 1;
        return nome.hashCode();
    }
}
