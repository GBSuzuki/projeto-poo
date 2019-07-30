package view;

import domain.MateriaPrima;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;
import service.interfaces.IEstoque;

import javax.inject.Inject;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

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
        NovaReceitaController.IngredientesNovaReceita.add(new NovaReceitaController.NovoMP(fieldMP.getText(), fielQtd.getText()));

        Stage stage = (Stage) botaoAdicionar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void botaoCancelar(){
        Stage stage = (Stage) botaoCancelar.getScene().getWindow();
        stage.close();
    }
}
