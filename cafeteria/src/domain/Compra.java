package domain;

import domain.interfaces.ATransacao;

import java.util.UUID;

public class Compra extends ATransacao {

    protected Compra(String nome, int quantidade, float valor) {
        super(nome, quantidade, valor);
    }

    @Override
    public float getValorTotal() {
        return getQuantidade()*getValor();
    }
}
