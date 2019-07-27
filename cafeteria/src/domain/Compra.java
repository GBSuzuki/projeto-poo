package domain;

import domain.interfaces.ATransacao;

import java.util.UUID;

public class Compra extends ATransacao {

    public Compra(String nome, int quantidade, float valor) {
        super(nome, quantidade, valor);
    }

    @Override
    public float getValorTotal() {
        return getQuantidade()*getValor();
    }

    @Override
    public String getTipo() {
        return "Compra";
    }
}
