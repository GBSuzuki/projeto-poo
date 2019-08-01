package view;

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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import service.interfaces.IBancoDeReceitas;
import service.interfaces.IEstoque;
import service.interfaces.ITranscaoService;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;

public class NovaReceitaController implements Initializable {
    private final ITranscaoService transacao;
    private final IEstoque estoque;
    private final IBancoDeReceitas receitas;
    public static ObservableList<NovoMP> IngredientesNovaReceita = FXCollections.observableArrayList();
    public static Receita selectedReceita;

    //Injeta o estoque
    @Inject
    public NovaReceitaController(IEstoque estoque, ITranscaoService transacao, IBancoDeReceitas receitas) {
        this.transacao = transacao;
        this.estoque = estoque;
        this.receitas = receitas;
        IngredientesNovaReceita.clear();
    }

    @FXML
    private TableView<NovoMP> tbData;

    @FXML
    public TableColumn<NovoMP, String> produto;

    @FXML
    public TableColumn<NovoMP, String> qtd;

    @FXML
    public TextField nomeReceita;

    @FXML
    public Button botaoCriarReceita;

    @FXML
    public Button botaoCancelar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Coluna 1 - Nome do produto
        produto.setCellValueFactory(new PropertyValueFactory<>("produto"));

        // Coluna 2 - quantidade de produto
        qtd.setCellValueFactory(new PropertyValueFactory<>("qtd"));

        tbData.setItems(IngredientesNovaReceita);
        tbData.getColumns().clear();
        tbData.getColumns().addAll(produto, qtd);

        if(selectedReceita != null)
            editaReceita(selectedReceita);
    }

    @FXML
    public void criarReceita(){
        if (nomeReceita.getText() != null) {
            Receita nova = new Receita(nomeReceita.getText());

            if (!IngredientesNovaReceita.isEmpty()) {
                for (NovoMP x : IngredientesNovaReceita)
                    nova.addIngrediente(estoque.getMP(x.getProduto()).getId(), Integer.parseInt(x.getQtd()));

                receitas.AdicionaReceita(nova);

                Stage stage = (Stage) botaoCriarReceita.getScene().getWindow();
                stage.close();
            }
        }
    }

    @FXML
    private void botaoCancelar() {
        Stage stage = (Stage) botaoCancelar.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void adicionarMP() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SelMateriaPrima.fxml"));
            fxmlLoader.setControllerFactory(IoC.context::getInstance);
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Selecionar Produto");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void removeMP() {
        IngredientesNovaReceita.remove(tbData.getSelectionModel().getSelectedItem());
    }

    public void editaReceita(Receita receita){
        nomeReceita.setText(receita.getNomeReceita());

        for(Map.Entry<UUID, Integer> x : receita.getIngredientes().entrySet()){
            NovoMP mp = new NovaReceitaController.NovoMP(estoque.getMP(x.getKey()).getNome(), Integer.toString(x.getValue()));
            IngredientesNovaReceita.add(mp);
        }
    }

    public static class NovoMP {

        private final SimpleStringProperty produto;
        private final SimpleStringProperty qtd;

        public NovoMP(String produto, String qtd) {
            this.produto = new SimpleStringProperty(produto);
            this.qtd = new SimpleStringProperty(qtd);
        }

        public String getProduto() {
            return produto.get();
        }

        public void setProduto(String nome) {
            this.produto.set(nome);
        }

        public String getQtd() {
            return qtd.get();
        }

        public void setQtd(String qtd) {
            this.qtd.set(qtd);
        }
    }
}