package view;

import domain.MateriaPrima;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import service.interfaces.IEstoque;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

public class EstoqueController implements Initializable {

    private final IEstoque estoque;

    //Injeta o estoque
    @Inject
    public EstoqueController(IEstoque estoque){

        this.estoque = estoque;
    }

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

        uuid.setCellValueFactory(new PropertyValueFactory<>("Id"));
        nome.setCellValueFactory(new PropertyValueFactory<>("Nome"));
        preco.setCellValueFactory(new PropertyValueFactory<>("Preco"));
        qtd.setCellValueFactory(new PropertyValueFactory<>("Quantidade"));

        //EasyDI easyDI = new EasyDI();
        //easyDI.bindInterface(IEstoque.class, Estoque.class);
        //Estoque e = easyDI.getInstance(Estoque.class);

        ObservableList<MateriaPrima> Estoque = FXCollections.observableArrayList(
                // Inserir cada materia prima aqui.
        );

        //utiliza o estoque injetado para obter os materiais
        Estoque.addAll(estoque.getMateriais());

        //add your data to the table here.
        tbData.setItems(Estoque);
    }
}