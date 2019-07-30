package view;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.controlsfx.control.textfield.TextFields;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import domain.MateriaPrima;
import view.NovaReceitaController;
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
import service.Estoque;
import service.interfaces.IBancoDeReceitas;
import service.interfaces.IEstoque;
import service.interfaces.ITranscaoService;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.net.URL;

public class SelMateriaPrimaController implements Initializable {
    private final IEstoque estoque;
    private ArrayList<String> mps = new ArrayList<>();

    @FXML
    private TextField fieldMP;

    @FXML
    TextField fielQtd;

    @FXML
    private Button botaoCancelar;

    @FXML
    private Button botaoAdicionar;

    @Inject
    public SelMateriaPrimaController(IEstoque estoque) {
        this.estoque = estoque;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        for (MateriaPrima x : estoque.getMateriais()) {
            mps.add(x.getNome());
        }

        TextFields.bindAutoCompletion(fieldMP, mps);
    }

    @FXML
    public void botaoAdicionar(){
        NovaReceitaController.IngredientesNovaReceita.put(
                estoque.getMP(fieldMP.getText()),
                fielQtd.getText()
        );

        Stage stage = (Stage) botaoAdicionar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void botaoCancelar(){
        Stage stage = (Stage) botaoCancelar.getScene().getWindow();
        stage.close();
    }
}
