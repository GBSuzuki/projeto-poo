package helpers;

import eu.lestard.easydi.EasyDI;
import service.BancoDeReceitas;
import service.Estoque;
import service.PersistenceService;
import service.TransacaoService;
import service.interfaces.IBancoDeReceitas;
import service.interfaces.IEstoque;
import service.interfaces.IPersistenceService;
import service.interfaces.ITranscaoService;

public class IoC {
    public static EasyDI context;
    static {
        context = new EasyDI();
        context.bindInterface(IEstoque .class, Estoque .class);
        context.bindInterface(IBancoDeReceitas .class, BancoDeReceitas .class);
        context.bindInterface(ITranscaoService .class, TransacaoService .class);
        context.bindInterface(IPersistenceService .class, PersistenceService .class);
    }
}
