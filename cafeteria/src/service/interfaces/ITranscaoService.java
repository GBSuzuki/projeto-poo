package service.interfaces;

import domain.interfaces.ATransacao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.UUID;

public interface ITranscaoService {
    boolean efetuaCompra(ATransacao compra);
    boolean removeCompra(UUID Id);
    boolean efetuaVenda(ATransacao venda);
    ArrayList<ATransacao> getVendas(Timestamp date);
    ArrayList<ATransacao> getCompras(Timestamp date);
}
