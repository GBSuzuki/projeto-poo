
import domain.Receita;
import eu.lestard.easydi.EasyDI;
import service.BancoDeReceitas;
import service.Estoque;
import service.TransacaoService;
import service.interfaces.IBancoDeReceitas;
import service.interfaces.IEstoque;
import service.interfaces.ITranscaoService;

public class Main {
    public static void main(String[] args) {
        EasyDI easyDI = new EasyDI();
        easyDI.bindInterface(IEstoque.class, Estoque.class);
        easyDI.bindInterface(IBancoDeReceitas.class, BancoDeReceitas.class);
        easyDI.bindInterface(ITranscaoService.class, TransacaoService.class);
        BancoDeReceitas a = easyDI.getInstance(BancoDeReceitas.class);
        a.AdicionaReceita(new Receita("Teste"));
        System.out.println(a.getReceita("Teste").getNomeReceita());
    }
}
