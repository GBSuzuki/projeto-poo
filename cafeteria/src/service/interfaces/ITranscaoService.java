package service.interfaces;

import domain.interfaces.ATransacao;

public interface ITranscaoService {
    boolean efetuaCompra(ATransacao compra);
    boolean efetuaVenda(ATransacao venda);
}
