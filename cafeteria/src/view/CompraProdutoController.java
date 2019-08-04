package view;

import domain.Compra;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.interfaces.ITranscaoService;


public class CompraProdutoController {
    private final ITranscaoService transacao;

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

    public CompraProdutoController(ITranscaoService transacao) {
        this.transacao = transacao;
    }

    @FXML
    private void botaoCancelar(){
        Stage stage = (Stage) botaoCancelar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void botaoAdicionar(){
        transacao.efetuaCompra(new Compra(fieldNome.getText(), Integer.parseInt(fieldQuantidade.getText()), Float.parseFloat(fieldPreco.getText())));
        
        Stage stage = (Stage) botaoAdicionar.getScene().getWindow();
        stage.close();
    }
}
