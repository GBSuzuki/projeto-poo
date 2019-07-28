/* Todas os métodos abaixo foram testados (21/07/2019)
 * e funcionaram como esperado. */

package service;

import domain.MateriaPrima;
import service.interfaces.IEstoque;
import service.interfaces.IPersistenceService;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.UUID;

@Singleton
public class Estoque implements IEstoque {
    private ArrayList<MateriaPrima> Materiais;
    private IPersistenceService persistenceService;
    @Inject
    public Estoque(IPersistenceService persistenceService) {
        this.persistenceService = persistenceService;
        //Carregar dados do json em Materiais
        Materiais = persistenceService.getEstoque();
    }

    /* Método remove MateriaPrima do estoque */
    public void RemoveMP(UUID id_MP) {
        Materiais.removeIf(x -> x.getId() == id_MP);

        //Remover do json
        persistenceService.setEstoque(Materiais);
    }

    /* Método adiciona matéria prima ao estoque, verifica se já existe alguma
     * com mesmo nome, se já existir apenas atualiza os dados.*/
    public void AdicionaMP(String nome, int quantidade, float preco) {
        if (Materiais.stream().noneMatch(x -> x.getNome().equals(nome)))
            Materiais.add(new MateriaPrima(nome, quantidade, preco));
        else {
            MateriaPrima mp = getMP(nome);
            mp.updateQuantidade(quantidade);
            mp.setPreco(preco);
        }

        persistenceService.setEstoque(Materiais);
    }

    /* Método retorna MateriaPrima de acordo com nome,
     * caso não encontre retorna nulo. */
    public MateriaPrima getMP(String nome) {
        return Materiais.stream().filter(x -> x.getNome().equals(nome)).findAny().orElse(null);
    }

    /* Método retorna MateriaPrima de acordo com nome,
     * caso não encontre retorna nulo. */
    public MateriaPrima getMP(UUID Id) {
        return Materiais.stream().filter(x -> x.getId().equals(Id)).findAny().orElse(null);
    }

    public ArrayList<MateriaPrima> getMateriais() {
        return Materiais;
    }
}