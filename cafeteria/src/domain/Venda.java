package domain;

import domain.interfaces.ATransacao;

public class Venda extends ATransacao {
    protected Venda(String nome, int quantidade, float valor) {
        super(nome, quantidade, valor);
    }

    @Override
    public float getValorTotal() {
        return getQuantidade()*getValor();
    }
}
