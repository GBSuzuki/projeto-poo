package domain.interfaces;

public abstract class ATransacao {
    private final String nome;
    private final int quantidade;
    private final float valor;
    private String data;

    public String getData() {
        return data;
    }

    protected ATransacao(String nome, int quantidade, float valor, String data) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.valor = valor;
        this.data = data;
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

    public abstract String getTipo();
}
