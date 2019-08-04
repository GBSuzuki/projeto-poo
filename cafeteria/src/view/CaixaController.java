package view;

import domain.Venda;
import helpers.IoC;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import service.interfaces.IBancoDeReceitas;
import service.interfaces.IEstoque;
import service.interfaces.ITranscaoService;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CaixaController implements Initializable {
    private final ITranscaoService transacao;
    private final IBancoDeReceitas receitas;
    public static ObservableList<Compradas> receitasCompradas = FXCollections.observableArrayList();

    //Injeta o estoque
    @Inject
    public CaixaController(IBancoDeReceitas receitas, ITranscaoService transacao, IEstoque estoque) {
        this.transacao = transacao;
        this.receitas = receitas;
    }

    @FXML
    private TableView<Compradas> tbData;

    @FXML
    public TableColumn<Compradas, String> nome;

    @FXML
    public TableColumn<Compradas, String> preco;

    @FXML
    public Label precoFinal;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Coluna 1 - Nome do produto
        nome.setCellValueFactory(new PropertyValueFactory<>("produto"));

        // Coluna 2 - quantidade de produto
        preco.setCellValueFactory(new PropertyValueFactory<>("preco"));

        tbData.setItems(receitasCompradas);
        tbData.getColumns().clear();
        tbData.getColumns().addAll(nome, preco);
    }

    @FXML
    public void removeReceita() {
        if (tbData.getSelectionModel().getSelectedItem() != null) {
            receitasCompradas.remove(tbData.getSelectionModel().getSelectedItem());
        } else {
            Alert dialogoInfo = new Alert(Alert.AlertType.INFORMATION);
            dialogoInfo.setTitle("Remover Item");
            dialogoInfo.setHeaderText("Nenhum Item selecionado");
            dialogoInfo.setContentText("Você deve selecionar um item para poder\nremover.");
            dialogoInfo.showAndWait();
        }
    }

    @FXML
    public void adicionarReceita() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SelReceita.fxml"));
            fxmlLoader.setControllerFactory(IoC.context::getInstance);
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Selecionar Receita");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
            stage.setOnHiding(event -> {
                atualizaPreco();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void Pagar(){
        if(!receitasCompradas.isEmpty()) {

            for (Compradas x : receitasCompradas){
                transacao.efetuaVenda(new Venda(receitas.getReceita(x.getProduto()).getNomeReceita(), 1, receitas.ObterPreco(x.getProduto())));
            }
            receitasCompradas.clear();
            atualizaPreco();

            Alert dialogoInfo = new Alert(Alert.AlertType.INFORMATION);
            dialogoInfo.setTitle("Compra Finalizada");
            dialogoInfo.setHeaderText(null);
            dialogoInfo.setContentText("Pagamento efetuado com sucesso!");
            dialogoInfo.showAndWait();
        }
    }

    @FXML
    private void Cancelar(){
        if(!receitasCompradas.isEmpty()) {
            receitasCompradas.clear();
            atualizaPreco();

            Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
            dialogoInfo.setTitle("Compra Cancelada");
            dialogoInfo.setHeaderText(null);
            dialogoInfo.setContentText("Compra cancelada pelo operador!");
            dialogoInfo.showAndWait();
        }
    }

    private void atualizaPreco() {
        float precoFinalFloat = 0;

        for (Compradas x : receitasCompradas) {
            precoFinalFloat += receitas.ObterPreco(x.getProduto());
        }

        precoFinal.setText("R$ " + precoFinalFloat + "0");
    }

    public static class Compradas {

        private final SimpleStringProperty produto;
        private final SimpleStringProperty preco;

        public Compradas(String produto, String preco) {
            this.produto = new SimpleStringProperty(produto);
            this.preco = new SimpleStringProperty(preco);
        }

        public String getProduto() {
            return produto.get();
        }

        public void setProduto(String nome) {
            this.produto.set(nome);
        }

        public String getPreco() {
            return preco.get();
        }

        public void setPreco(String preco) {
            this.produto.set(preco);
        }
    }
}