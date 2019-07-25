package service.interfaces;

import domain.MateriaPrima;

import java.util.ArrayList;
import java.util.UUID;

public interface IEstoque {
    void RemoveMP(UUID id_MP);

    void AdicionaMP(String nome, int quantidade, float preco);

    MateriaPrima getMP(String nome);

    MateriaPrima getMP(UUID Id);

    ArrayList<MateriaPrima> getMateriais();
}
