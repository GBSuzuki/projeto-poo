package service;

import domain.Receita;
import service.interfaces.IBancoDeReceitas;
import service.interfaces.IEstoque;
import service.interfaces.IPersistenceService;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.*;

@Singleton
public class BancoDeReceitas implements IBancoDeReceitas {

    private final IEstoque estoque;
    private final IPersistenceService persistenceService;
    private ArrayList<Receita> Receitas;
    private Map<UUID, Integer> produtosFinais;

    @Inject
    public BancoDeReceitas(IEstoque estoque, IPersistenceService persistenceService) {
        this.estoque = estoque;
        this.persistenceService = persistenceService;
        this.produtosFinais = new HashMap<>();
        Receitas = new ArrayList<>();
        Receitas = persistenceService.getBancoReceitas();
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
                map(x -> estoque.getMP(x.getKey()).getPreco() * x.getValue()).
                reduce((float) 0.0, (x, y) -> x + y);
    }

    public float ObterPreco(String NomeReceita) {
        return getReceita(NomeReceita).getIngredientes().entrySet().
                stream().
                map(x -> estoque.getMP(x.getKey()).getPreco() * x.getValue()).
                reduce((float) 0.0, (x, y) -> x + y);
    }

    public void AdicionaReceita(Receita receita) {
        Receitas.removeIf(x -> x.getNomeReceita().equals(receita.getNomeReceita()));
        Receitas.add(receita);
        persistenceService.setBancoReceitas(Receitas);
    }

    public void RemoveReceita(UUID Id) {
        Receitas.removeIf(x -> x.getId().equals(Id));
        persistenceService.setBancoReceitas(Receitas);
    }

    public void RemoveReceita(String NomeReceita) {
        Receitas.removeIf(x -> x.getNomeReceita().equals(NomeReceita));
        persistenceService.setBancoReceitas(Receitas);
    }

    public int attDisp(UUID Id) {
        return getReceita(Id)
                .getIngredientes()
                .entrySet()
                .stream()
                .map(x -> estoque.getMP(x.getKey()).getQuantidade() / x.getValue())
                .min(Comparator.comparing(Float::valueOf))
                .orElse(0);
    }

    public int obterDisp(UUID Id) {
        return this.attDisp(Id);
    }

    public Map<UUID, Integer> obterDispAll() {
        return produtosFinais;
    }
}