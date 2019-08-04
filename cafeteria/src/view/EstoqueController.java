package view;

import domain.MateriaPrima;
import helpers.IoC;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import service.interfaces.IBancoDeReceitas;
import service.interfaces.IEstoque;
import service.interfaces.ITranscaoService;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class EstoqueController implements Initializable {
    private final ITranscaoService transacao;
    private final IEstoque estoque;

    //Injeta o estoque
    @Inject
    public EstoqueController(IEstoque estoque, ITranscaoService transacao, IBancoDeReceitas receitas) {
        this.transacao = transacao;
        this.estoque = estoque;
    }

    @FXML
    private TableView<MateriaPrima> tbData;

    @FXML
    public TableColumn<MateriaPrima, String> nome;

    @FXML
    public TableColumn<MateriaPrima, String> preco;

    @FXML
    TableColumn<MateriaPrima, String> qtd;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nome.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNome()));
        preco.setCellValueFactory(c -> new SimpleStringProperty(Float.toString(c.getValue().getPreco())));
        qtd.setCellValueFactory(c -> new SimpleStringProperty(Integer.toString(c.getValue().getQuantidade())));

        tbData.getItems().addAll(estoque.getMateriais());
    }

    public void reloadList() {
        tbData.getItems().clear();
        tbData.getItems().addAll(estoque.getMateriais());
        tbData.getSortOrder().add(nome);
    }

    @FXML
    public void adicionarMP() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CompraProduto.fxml"));
            fxmlLoader.setControllerFactory(IoC.context::getInstance);
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Comprar Produto");
            stage.setScene(new Scene(root1));
            stage.setResizable(false);
            stage.show();

            stage.setOnHiding(event -> {
                reloadList();
            });

        } catch (Exception e) {
            Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
            dialogoInfo.setTitle("Erro Inesperado");
            dialogoInfo.setHeaderText(null);
            dialogoInfo.setContentText(e.getMessage());
            dialogoInfo.showAndWait();
            e.printStackTrace();
        }
    }

    @FXML
    public void removeMP() {
        if (tbData.getSelectionModel().getSelectedItem() != null) {
            transacao.removeCompra(tbData.getSelectionModel().getSelectedItem().getId());
            reloadList();
        } else {
            Alert dialogoInfo = new Alert(Alert.AlertType.INFORMATION);
            dialogoInfo.setTitle("Remover Produto");
            dialogoInfo.setHeaderText("Nenhum produto selecionado");
            dialogoInfo.setContentText("Você deve selecionar um produto para poder\nremover.");
            dialogoInfo.showAndWait();
        }
    }
}