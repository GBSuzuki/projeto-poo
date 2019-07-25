package service.interfaces;

import domain.MateriaPrima;
import domain.Receita;

import java.util.ArrayList;

public interface IPersistenceService {
    ArrayList<MateriaPrima> getEstoque();

    ArrayList<Receita> getBancoReceitas();

    void setEstoque(ArrayList<MateriaPrima> materiaPrimas);

    void setBancoReceitas(ArrayList<Receita> receitas);
}
