package service.interfaces;

import domain.Receita;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

public interface IBancoDeReceitas {
    ArrayList<Receita> getReceitas();

    Receita getReceita(UUID Id);

    Receita getReceita(String NomeReceita);

    float ObterPreco(UUID Id);

    float ObterPreco(String NomeReceita);

    void AdicionaReceita(Receita receita);

    void RemoveReceita(UUID Id);

    void RemoveReceita(String NomeReceita);

    int attDisp(UUID Id);

    int obterDisp(UUID Id);

    Map<UUID, Integer> obterDispAll();
}
