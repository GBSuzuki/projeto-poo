package view;

import domain.Receita;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;
import service.interfaces.IBancoDeReceitas;
import service.interfaces.IEstoque;

import javax.inject.Inject;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SelReceitaController implements Initializable {
    private final IEstoque estoque;
    private final IBancoDeReceitas receitas;
    private ArrayList<String> mps = new ArrayList<>();

    @FXML
    private TextField fieldMP;

    @FXML
    private Button botaoCancelar;

    @FXML
    private Button botaoAdicionar;

    @Inject
    public SelReceitaController(IEstoque estoque, IBancoDeReceitas receitas) {
        this.estoque = estoque;
        this.receitas = receitas;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (Receita x : receitas.getReceitas()) {
            mps.add(x.getNomeReceita());
        }

        TextFields.bindAutoCompletion(fieldMP, mps).setMaxWidth(150);
    }

    @FXML
    public void botaoAdicionar() {
        if (!fieldMP.getText().isEmpty()) {
            if(receitas.getReceita(fieldMP.getText()) == null)
            {
                Alert dialogoInfo = new Alert(Alert.AlertType.INFORMATION);
                dialogoInfo.setTitle("Receita");
                dialogoInfo.setHeaderText("Receita não existente");
                dialogoInfo.setContentText("Favor contruir a receita antes de realizar uma venda.");
                dialogoInfo.showAndWait();
                fieldMP.setText("");
            }
            else {
                CaixaController.receitasCompradas.add(new CaixaController.Compradas(fieldMP.getText(), Float.toString(receitas.ObterPreco(fieldMP.getText()))));

                Stage stage = (Stage) botaoAdicionar.getScene().getWindow();
                stage.close();
            }
        }
    }

    @FXML
    private void botaoCancelar() {
        Stage stage = (Stage) botaoCancelar.getScene().getWindow();
        stage.close();
    }
}
