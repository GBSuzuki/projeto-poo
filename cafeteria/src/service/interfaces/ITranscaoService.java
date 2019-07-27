package service.interfaces;

import domain.interfaces.ATransacao;

import java.sql.Timestamp;
import java.util.ArrayList;

public interface ITranscaoService {
    boolean efetuaCompra(ATransacao compra);
    boolean efetuaVenda(ATransacao venda);
    ArrayList<ATransacao> getVendas(Timestamp date);
    ArrayList<ATransacao> getCompras(Timestamp date);
}
