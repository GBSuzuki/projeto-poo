package domain;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Receita {
    private UUID Id;
    private String NomeReceita;
    private Map<UUID, Integer> Ingredientes;

    public Receita(String NomeReceita) {
        Ingredientes = new HashMap<>();
        Id = UUID.randomUUID();
        this.NomeReceita = NomeReceita;
    }

    public UUID getId() {
        return Id;
    }

    public String getNomeReceita() {
        return NomeReceita;
    }

    public Map<UUID, Integer> getIngredientes() {
        return Ingredientes;
    }

    public void setIngredientes(Map<UUID, Integer> ingredientes) {
        Ingredientes = ingredientes;
    }

    public void addIngrediente(UUID id, Integer quant)
    {
        Ingredientes.put(id, quant);
    }

    public void AdicionaMP(UUID Id, int quantidade) {
        Ingredientes.put(Id, quantidade);
    }

    /* Remove uma quantidade específica de matéria prima */
    public void RemoveMP(UUID Id, int quantidade) {
        int qtdAtual = Ingredientes.get(Id);

        /* Verifica se quantidade que deseja ser removida é mais
         * do que se tem, se sim remove a chave do ingrediente do
         * HashMap.*/
        if (qtdAtual - quantidade <= 0)
            RemoveMP(Id);
        else
            Ingredientes.put(Id, qtdAtual - quantidade);
    }

    /* Remove toda matéria prima especificada */
    public void RemoveMP(UUID Id) {
        Ingredientes.remove(Id);
    }
}