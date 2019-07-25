package service;


import domain.Receita;
import helpers.jsonKIT;
import service.interfaces.IBancoDeReceitas;
import service.interfaces.IEstoque;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.UUID;

@Singleton
public class BancoDeReceitas implements IBancoDeReceitas {

    private final IEstoque estoque;
    private ArrayList<Receita> Receitas;

    @Inject
    public BancoDeReceitas(IEstoque estoque) {
        this.estoque = estoque;
        Receitas = new ArrayList<>();
        Receitas = jsonKIT.getJsonBancoReceitas();
    }

    public ArrayList<Receita> getReceitas() {
        return Receitas;
    }

    public Receita getReceita(UUID Id) {
        Receita retorno = Receitas.stream().filter(x -> x.getId().equals(Id)).findAny().orElse(null);
        return retorno;
    }

    public Receita getReceita(String NomeReceita) {
        Receita retorno = Receitas.stream().filter(x -> x.getNomeReceita().equals(NomeReceita)).findAny().orElse(null);
        return retorno;
    }

    public float ObterPreco(UUID Id) {
        return getReceita(Id).getIngredientes().entrySet().
                stream().
                map(x -> estoque.getMP(x.getKey()).getPreco()*x.getValue()).
                reduce((float) 0.0, (x, y) -> x + y);
    }

    public float ObterPreco(String NomeReceita) {
        return getReceita(NomeReceita).getIngredientes().entrySet().
                stream().
                map(x -> estoque.getMP(x.getKey()).getPreco()*x.getValue()).
                reduce((float) 0.0, (x, y) -> x + y);
    }

    public void AdicionaReceita(Receita receita) {
        Receitas.add(receita);
        jsonKIT.setJSON(Receitas, "BancoReceitas.json");
    }

    public void RemoveReceita(UUID Id) {
        Receitas.removeIf(x -> x.getId().equals(Id));
        jsonKIT.setJSON(Receitas, "BancoReceitas.json");
    }

    public void RemoveReceita(String NomeReceita) {
        Receitas.removeIf(x -> x.getNomeReceita().equals(NomeReceita));
        jsonKIT.setJSON(Receitas, "BancoReceitas.json");
    }
}