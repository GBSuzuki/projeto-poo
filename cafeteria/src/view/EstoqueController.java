package view;

import domain.MateriaPrima;
import eu.lestard.easydi.EasyDI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import service.Estoque;
import service.interfaces.IEstoque;
import service.interfaces.IPersistenceService;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

public class EstoqueController implements Initializable {
    @FXML
    private TableView<MateriaPrima> tbData;

    @FXML
    public TableColumn<MateriaPrima, UUID> uuid;

    @FXML
    public TableColumn<MateriaPrima, String> nome;

    @FXML
    public TableColumn<MateriaPrima, Integer> preco;

    @FXML
    TableColumn<MateriaPrima, Integer> qtd;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        uuid.setCellValueFactory(new PropertyValueFactory<>("UUID"));
        nome.setCellValueFactory(new PropertyValueFactory<>("Nome"));
        preco.setCellValueFactory(new PropertyValueFactory<>("Preco"));
        qtd.setCellValueFactory(new PropertyValueFactory<>("Quantidade"));

        //EasyDI easyDI = new EasyDI();
        //easyDI.bindInterface(IEstoque.class, Estoque.class);
        //Estoque e = easyDI.getInstance(Estoque.class);

        ObservableList<MateriaPrima> Estoque = FXCollections.observableArrayList(
                // Inserir cada materia prima aqui.
        );

        //add your data to the table here.
        tbData.setItems(Estoque);
    }
}
