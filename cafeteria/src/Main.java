import domain.Compra;
import domain.Receita;
import domain.Venda;
import eu.lestard.easydi.EasyDI;
import service.BancoDeReceitas;
import service.Estoque;
import service.PersistenceService;
import service.TransacaoService;
import service.interfaces.IBancoDeReceitas;
import service.interfaces.IEstoque;
import service.interfaces.IPersistenceService;
import service.interfaces.ITranscaoService;

public class Main {
    public static void main(String[] args) {
        EasyDI easyDI = new EasyDI();
        easyDI.bindInterface(IEstoque.class, Estoque.class);
        easyDI.bindInterface(IBancoDeReceitas.class, BancoDeReceitas.class);
        easyDI.bindInterface(ITranscaoService.class, TransacaoService.class);
        easyDI.bindInterface(IPersistenceService.class, PersistenceService.class);

        BancoDeReceitas a = easyDI.getInstance(BancoDeReceitas.class);
        Estoque e = easyDI.getInstance(Estoque.class);
        TransacaoService t = easyDI.getInstance(TransacaoService.class);

        t.efetuaCompra(new Compra("Cafe", 5, 4));
        t.efetuaCompra(new Compra("Leite", 10, 6));

        Receita cafecleite = new Receita("cafecomleite");

        cafecleite.addIngrediente(e.getMP("Cafe").getId(), 1);
        cafecleite.addIngrediente(e.getMP("Leite").getId(), 1);

        a.AdicionaReceita(cafecleite);

        t.efetuaVenda(new Venda("cafecomleite", 1, 15));
    }
}
