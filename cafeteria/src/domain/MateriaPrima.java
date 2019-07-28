package domain;

import java.util.UUID;

public class MateriaPrima {
    private UUID Id;
    private String Nome;
    private int Quantidade;
    private float Preco;

    public MateriaPrima() {
        Id = UUID.randomUUID();
    }

    public MateriaPrima(String nome, int quantidade, float preco) {
        Id = UUID.randomUUID();
        Nome = nome;
        Quantidade = quantidade;
        Preco = preco;
    }

    public UUID getId() {
        return Id;
    }

    public void setId(UUID id) {
        Id = id;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public int getQuantidade() {
        return Quantidade;
    }

    public void setQuantidade(int quantidade) {
        Quantidade = quantidade;
    }

    public float getPreco() {
        return Preco;
    }

    public void setPreco(float preco) {
        Preco = preco;
    }

    public void updateQuantidade(int quantidade) {
        Quantidade += quantidade;
    }
}