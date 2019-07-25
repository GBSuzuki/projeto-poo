package service;

import domain.Receita;
import domain.interfaces.ATransacao;
import service.interfaces.IBancoDeReceitas;
import service.interfaces.IEstoque;
import service.interfaces.ITranscaoService;

import javax.inject.Inject;
import java.util.Map;
import java.util.UUID;

public class TransacaoService implements ITranscaoService {

    private final IBancoDeReceitas bancoDeReceitas;
    private final IEstoque estoque;

    @Inject
    public TransacaoService(IBancoDeReceitas bancoDeReceitas, IEstoque estoque) {
        this.bancoDeReceitas = bancoDeReceitas;
        this.estoque = estoque;
    }

    @Override
    public boolean efetuaCompra(ATransacao compra) {
        try {
            estoque.AdicionaMP(compra.getNome(), compra.getQuantidade(), compra.getValor());
            return true;
        }
        catch (Exception ex) {
            return false;
        }
    }

    @Override
    public boolean efetuaVenda(ATransacao venda) {
        Receita receita = bancoDeReceitas.getReceita(venda.getNome());
        for(Map.Entry<UUID, Integer> entry : receita.getIngredientes().entrySet())
            if(estoque.getMP(entry.getKey()).getQuantidade() < entry.getValue() * venda.getQuantidade())
                return false;
        return true;
    }
}
