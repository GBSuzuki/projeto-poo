package view;

import domain.MateriaPrima;
import helpers.IoC;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import service.interfaces.IBancoDeReceitas;
import service.interfaces.IEstoque;
import service.interfaces.ITranscaoService;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EstoqueController implements Initializable {
    private final ITranscaoService transacao;
    private final IEstoque estoque;
    private final IBancoDeReceitas receitas;

    private ObservableList<MateriaPrima> Estoque = FXCollections.observableArrayList();

    //Injeta o estoque
    @Inject
    public EstoqueController(IEstoque estoque, ITranscaoService transacao, IBancoDeReceitas receitas) {
        this.transacao = transacao;
        this.estoque = estoque;
        this.receitas = receitas;
    }

    @FXML
    private TableView<MateriaPrima> tbData;

    @FXML
    public TableColumn<MateriaPrima, String> nome;

    @FXML
    public TableColumn<MateriaPrima, String> preco;

    @FXML
    TableColumn<MateriaPrima, String> qtd;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nome.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNome()));
        preco.setCellValueFactory(c -> new SimpleStringProperty(Float.toString(c.getValue().getPreco())));
        qtd.setCellValueFactory(c -> new SimpleStringProperty(Integer.toString(c.getValue().getQuantidade())));

        tbData.getItems().addAll(estoque.getMateriais());
    }

    public void reloadList() {
        tbData.getItems().clear();
        tbData.getItems().addAll(estoque.getMateriais());
    }

    @FXML
    public void adicionarMP() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CompraProduto.fxml"));
            fxmlLoader.setControllerFactory(IoC.context::getInstance);
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Comprar Produto");
            stage.setScene(new Scene(root1));
            stage.setResizable(false);
            stage.show();

            stage.setOnHiding(event -> {
                reloadList();
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void removeMP() {
        transacao.removeCompra(tbData.getSelectionModel().getSelectedItem().getId());
        reloadList();
    }
}