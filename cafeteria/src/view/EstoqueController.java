package view;

import domain.MateriaPrima;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import service.interfaces.IEstoque;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

public class EstoqueController implements Initializable {

    private final IEstoque estoque;
    private ObservableList<MateriaPrima> Estoque = FXCollections.observableArrayList();

    //Injeta o estoque
    @Inject
    public EstoqueController(IEstoque estoque) {

        this.estoque = estoque;
    }

    @FXML
    private TableView<MateriaPrima> tbData;

    /*@FXML
    public TableColumn<MateriaPrima, UUID> uuid;*/

    @FXML
    public TableColumn<MateriaPrima, String> nome;

    @FXML
    public TableColumn<MateriaPrima, Integer> preco;

    @FXML
    TableColumn<MateriaPrima, Integer> qtd;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nome.setCellValueFactory(new PropertyValueFactory<>("Nome"));
        preco.setCellValueFactory(new PropertyValueFactory<>("Preco"));
        qtd.setCellValueFactory(new PropertyValueFactory<>("Quantidade"));

        Estoque.addAll(estoque.getMateriais());

        tbData.setItems(Estoque);
    }

    public void adicionarMP() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CompraProduto.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Comprar Produto");
            stage.setScene(new Scene(root1));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}