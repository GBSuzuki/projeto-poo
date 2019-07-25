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

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public float getPreco() {
        return Preco;
    }

    public void setPreco(float preco) {
        Preco = preco;
    }

    public int getQuantidade() {
        return Quantidade;
    }

    public void updateQuantidade(int quantidade) {
        Quantidade += quantidade;
    }
}