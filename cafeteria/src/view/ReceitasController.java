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

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ReceitasController implements Initializable {
    private final IBancoDeReceitas receitas;

    //Injeta o estoque
    @Inject
    public ReceitasController(IBancoDeReceitas receitas) {
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nome.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNomeReceita()));
        preco.setCellValueFactory(c -> new SimpleStringProperty(Float.toString(receitas.ObterPreco(c.getValue().getNomeReceita())))
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
            NovaReceitaController.selectedReceita = null;
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NovaReceita.fxml"));
            fxmlLoader.setControllerFactory(IoC.context::getInstance);
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Criar Receita");
            stage.setScene(new Scene(root));
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
    public void editaReceita() {
        try {
            NovaReceitaController.selectedReceita = tbData.getSelectionModel().getSelectedItem();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NovaReceita.fxml"));
            fxmlLoader.setControllerFactory(IoC.context::getInstance);
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Editar Receita");
            stage.setScene(new Scene(root));
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
    public void removeReceita() {
        receitas.RemoveReceita(tbData.getSelectionModel().getSelectedItem().getId());
        reloadList();
    }
}