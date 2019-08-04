import helpers.IoC;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.EstoqueController;
import view.ReceitasController;

import java.io.IOException;

public class Main extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private TabPane rootTabPane;
    FXMLLoader EstoqueTabLoader;
    FXMLLoader CaixaTabLoader;
    FXMLLoader ReceitasTabLoader;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Cafeteria");

        initRootLayout();
        addCaixaTab();
        addEstoqueTab();
        addReceitasTab();

        rootTabPane.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Tab>() {
                    @Override
                    public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
                        EstoqueController estoque = (EstoqueController)(EstoqueTabLoader.getController());
                        estoque.reloadList();

                        ReceitasController receitas = (ReceitasController)(ReceitasTabLoader.getController());
                        receitas.reloadList();
                    }
                }
        );
    }

    /**
     * Inicializa o root layout (layout base).
     */
    public void initRootLayout() {
        try {
            // Carrega o root layout do arquivo fxml.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/RootLayout.fxml"));

            //fala para o loader utilizar o easydi para instanciar a classe
            loader.setControllerFactory(IoC.context::getInstance);
            rootLayout = (BorderPane) loader.load();
            rootTabPane = (TabPane) rootLayout.getCenter();

            // Mostra a scene (cena) contendo o root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adiciona a tab de Receitas
     */
    public void addEstoqueTab() {
        try {
            // Carrega o FXML do estoque.
            EstoqueTabLoader = new FXMLLoader();
            EstoqueTabLoader.setLocation(Main.class.getResource("view/Estoque.fxml"));
            EstoqueTabLoader.setControllerFactory(IoC.context::getInstance);
            BorderPane estoqueOverview = (BorderPane) EstoqueTabLoader.load();

            //Cria a tab 1 e define o conteudo do fxml.
            Tab tabEstoque = new Tab();
            tabEstoque.setText("Estoque");
            tabEstoque.setContent(estoqueOverview);
            rootTabPane.getTabs().add(tabEstoque);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addReceitasTab() {
        try {
            // Carrega o FXML do estoque.
            ReceitasTabLoader = new FXMLLoader();
            ReceitasTabLoader.setLocation(Main.class.getResource("view/Receitas.fxml"));
            ReceitasTabLoader.setControllerFactory(IoC.context::getInstance);
            BorderPane receitasOverview = (BorderPane) ReceitasTabLoader.load();

            //Cria a tab 1 e define o conteudo do fxml.
            Tab tabReceitas = new Tab();
            tabReceitas.setText("Receitas");
            tabReceitas.setContent(receitasOverview);
            rootTabPane.getTabs().add(tabReceitas);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addCaixaTab() {
        try {
            // Carrega o FXML do estoque.
            CaixaTabLoader = new FXMLLoader();
            CaixaTabLoader.setLocation(Main.class.getResource("view/Caixa.fxml"));
            CaixaTabLoader.setControllerFactory(IoC.context::getInstance);
            BorderPane caixaOverview = (BorderPane) CaixaTabLoader.load();

            //Cria a tab 1 e define o conteudo do fxml.
            Tab tabCaixa = new Tab();
            tabCaixa.setText("Caixa");
            tabCaixa.setContent(caixaOverview);
            rootTabPane.getTabs().add(tabCaixa);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retorna o palco principal.
     *
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {

//        BancoDeReceitas a = context.getInstance(BancoDeReceitas.class);
//        Estoque e = context.getInstance(Estoque.class);
        //TransacaoService t = context.getInstance(TransacaoService.class);
//
//        t.efetuaCompra(new Compra("Cafe", 5, 4));
//        t.efetuaCompra(new Compra("Leite", 10, 6));
        //t.efetuaCompra(new Compra("Avelã", 15, 8));

//
//        Receita cafecleite = new Receita("cafecomleite");
//
//        cafecleite.addIngrediente(e.getMP("Cafe").getId(), 1);
//        cafecleite.addIngrediente(e.getMP("Leite").getId(), 1);
//
//        a.AdicionaReceita(cafecleite);
//
//        t.efetuaVenda(new Venda("cafecomleite", 1, 15));
//
//        var co = t.getCompras(new Timestamp(System.currentTimeMillis()));
//        var ce = t.getVendas(new Timestamp(System.currentTimeMillis()));

        launch(args);
    }
}
