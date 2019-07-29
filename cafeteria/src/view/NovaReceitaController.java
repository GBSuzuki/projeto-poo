package view;

import domain.MateriaPrima;
import domain.Receita;
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
import java.util.*;

public class NovaReceitaController implements Initializable {
    private final ITranscaoService transacao;
    private final IEstoque estoque;
    private final IBancoDeReceitas receitas;
    private ArrayList<MateriaPrima> selProdutos = new ArrayList<>();
    public static Map<MateriaPrima, String> IngredientesNovaReceita;

    static {
        IngredientesNovaReceita = new HashMap<>();
    }

    //Injeta o estoque
    @Inject
    public NovaReceitaController(IEstoque estoque, ITranscaoService transacao, IBancoDeReceitas receitas) {
        this.transacao = transacao;
        this.estoque = estoque;
        this.receitas = receitas;
        IngredientesNovaReceita = new HashMap<>();
    }

    @FXML
    private TableView<MateriaPrima> tbData;

    @FXML
    public TableColumn<MateriaPrima, String> produtos;

    @FXML
    TableColumn<MateriaPrima, String> qtd;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        produtos.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNome()));
        qtd.setCellValueFactory(c -> new SimpleStringProperty(Integer.toString(c.getValue().getQuantidade())));

        tbData.getItems().addAll(selProdutos);
    }

    public void reloadList() {
        tbData.getItems().clear();
        tbData.getItems().addAll(selProdutos);
        for(var a : IngredientesNovaReceita.keySet())
        {
            tbData.getItems().add(a);
        }
    }

    @FXML
    public void adicionarMP() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SelMateriaPrima.fxml"));
            fxmlLoader.setControllerFactory(IoC.context::getInstance);
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Selecionar Produto");
            stage.setScene(new Scene(root1));
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
        selProdutos.removeIf(x -> x.getId().equals(tbData.getSelectionModel().getSelectedItem().getId()));
        reloadList();
    }
}