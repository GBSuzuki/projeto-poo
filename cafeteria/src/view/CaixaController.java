package view;

import domain.Receita;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import service.interfaces.IBancoDeReceitas;
import service.interfaces.IEstoque;
import service.interfaces.ITranscaoService;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class CaixaController implements Initializable {
    private final ITranscaoService transacao;
    private final IBancoDeReceitas receitas;
    private final IEstoque estoque;
    public static ObservableList<Receita> Receitas = FXCollections.observableArrayList();

    //Injeta o estoque
    @Inject
    public CaixaController(IBancoDeReceitas receitas, ITranscaoService transacao, IEstoque estoque) {
        this.transacao = transacao;
        this.receitas = receitas;
        this.estoque = estoque;
    }

    @FXML
    private TableView<Receita> tbData;

    @FXML
    public TableColumn<Receita, String> nome;

    @FXML
    public TableColumn<Receita, String> preco;

    @FXML
    TableColumn<Receita, String> qtd;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nome.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNomeReceita()));
        //preco.setCellValueFactory(c -> new SimpleStringProperty(Float.toString(receitas.ObterPreco(c.getValue().getNomeReceita()))));

        tbData.getItems().addAll(Receitas);
    }

    public void reloadList() {
        tbData.getItems().clear();
        tbData.getItems().addAll(Receitas);
    }

    @FXML
    public void removeReceita() {

        reloadList();
    }
}