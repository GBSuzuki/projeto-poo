package domain;

import domain.interfaces.ATransacao;

public class Venda extends ATransacao {
    public Venda(String nome, int quantidade, float valor) {
        super(nome, quantidade, valor);
    }

    @Override
    public float getValorTotal() {
        return getQuantidade()*getValor();
    }

    @Override
    public String getTipo() {
        return "Venda";
    }
}
