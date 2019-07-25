package domain.interfaces;

public abstract class ATransacao {
    private final String nome;
    private final int quantidade;
    private final float valor;

    protected ATransacao(String nome, int quantidade, float valor) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.valor = valor;
    }

    public float getValor() {
        return valor;
    }

    public String getNome() {
        return nome;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public abstract float getValorTotal();
}
