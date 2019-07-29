package view;

import domain.Receita;
import helpers.IoC;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import service.interfaces.IBancoDeReceitas;
import service.interfaces.ITranscaoService;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ReceitasController implements Initializable {
    private final ITranscaoService transacao;
    private final IBancoDeReceitas receitas;

    //Injeta o estoque
    @Inject
    public ReceitasController(IBancoDeReceitas receitas, ITranscaoService transacao) {
        this.transacao = transacao;
        this.receitas = receitas;
    }

    @FXML
    private TableView<Receita> tbData;

    @FXML
    public TableColumn<Receita, String> nome;

    @FXML
    public TableColumn<Receita, String> preco;

    @FXML
    TableColumn<Receita, String> qtd;

    public static Map<UUID, Integer> IngredientesNovaReceita;

    static {
        IngredientesNovaReceita = new HashMap<>();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        receitas.attDisp();
        nome.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNomeReceita()));
        preco.setCellValueFactory(c -> new SimpleStringProperty(
                Float.toString(receitas.ObterPreco(c.getValue().getNomeReceita())))
        );
        qtd.setCellValueFactory(c -> new SimpleStringProperty(Integer.toString(receitas.obterDisp(c.getValue().getId()))));

        tbData.getItems().addAll(receitas.getReceitas());
    }

    public void reloadList() {
        tbData.getItems().clear();
        tbData.getItems().addAll(receitas.getReceitas());
    }

    @FXML
    public void criaReceita() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NovaReceita.fxml"));
            fxmlLoader.setControllerFactory(IoC.context::getInstance);
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Criar Receita");
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
    public void removeReceita() {
        receitas.RemoveReceita(tbData.getSelectionModel().getSelectedItem().getId());
        reloadList();
    }
}