package domain;

import service.interfaces.IBancoDeReceitas;
import service.interfaces.IEstoque;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Singleton
public class ProdutosFinais {

    private final IEstoque estoque;
    private final IBancoDeReceitas bancoDeReceitas;
    /* HashMap que salva UUID da Receita e quantidade
     * disponível para ser produzida. */
    private Map<UUID, Integer> Produtos;

    @Inject
    public ProdutosFinais(IEstoque estoque, IBancoDeReceitas bancoDeReceitas) {
        this.estoque = estoque;
        this.bancoDeReceitas = bancoDeReceitas;
        Produtos = new HashMap<>();
    }

    /* Retorna o cardápio de produtos disponíveis no momento */
    public Map<UUID, Integer> getProdutos() {
        return Produtos;
    }

    /* Retorna a quantidade que pode ser produzida do produto */
    public int getDisponivel(UUID Id) {
        return Produtos.get(Id);
    }

    public void calcProdutos() {
        /* Percorre o banco de receitas */
        for (Receita receita : bancoDeReceitas.getReceitas()) {
            /* Variável que salva a quantidade de materia prima limitante */
            int menorQuantidade = -1;

            /* Percorre o ingrediente de cada receita */
            for (UUID idIngrediente : receita.getIngredientes().keySet()) {

                /* Pega a quantidade de materia prima no estoque*/
                int qtdEstoque = estoque.getMP(idIngrediente).getQuantidade();
                int qtdProduto = 0;

                /* Verifica quantos * o produto pode ser produzido */
                while (qtdEstoque > 0) {
                    qtdEstoque = qtdEstoque - receita.getIngredientes().get(idIngrediente);
                    qtdProduto++;
                }

                /* Matem sempre o materia prima limitante */
                menorQuantidade = menorQuantidade == -1 || menorQuantidade > qtdProduto ? qtdProduto : menorQuantidade;

                /* Guarda a quantidade disponível da receita no HashMap atrelado ao UUID da receita */
                Produtos.put(receita.getId(), menorQuantidade);
            }
        }
    }
}