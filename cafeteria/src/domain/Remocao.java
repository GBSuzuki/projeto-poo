package domain;

import domain.interfaces.ATransacao;

public class Remocao extends ATransacao {
    public Remocao(String nome, int quantidade, float valor) {
        super(nome, quantidade, valor, "");
    }

    public Remocao(String nome, int quantidade, float valor, String data) {
        super(nome, quantidade, valor, data);
    }

    @Override
    public float getValorTotal() {
        return getQuantidade()*getValor();
    }

    @Override
    public String getTipo() {
        return "Remocao";
    }
}
