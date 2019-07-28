package view;

import domain.Compra;
import eu.lestard.easydi.EasyDI;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import service.BancoDeReceitas;
import service.Estoque;
import service.PersistenceService;
import service.TransacaoService;
import service.interfaces.IBancoDeReceitas;
import service.interfaces.IEstoque;
import service.interfaces.IPersistenceService;
import service.interfaces.ITranscaoService;


public class CompraProduto {
    private static EasyDI context;

    @FXML
    private Button botaoCancelar;

    @FXML
    private Button botaoAdicionar;

    @FXML
    private TextField fieldNome;

    @FXML
    private TextField fieldPreco;

    @FXML
    private TextField fieldQuantidade;

    public CompraProduto() {
        context = new EasyDI();
        context.bindInterface(IEstoque.class, Estoque.class);
        context.bindInterface(ITranscaoService.class, TransacaoService.class);
        context.bindInterface(IPersistenceService.class, PersistenceService.class);
    }

    @FXML
    private void botaoCancelar(){
        Stage stage = (Stage) botaoCancelar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void botaoAdicionar(){
        TransacaoService transacao = context.getInstance(TransacaoService.class);

        transacao.efetuaCompra(new Compra(fieldNome.getText(), Integer.parseInt(fieldQuantidade.getText()), Float.parseFloat(fieldPreco.getText())));

        Stage stage = (Stage) botaoAdicionar.getScene().getWindow();
        stage.close();
    }
}
