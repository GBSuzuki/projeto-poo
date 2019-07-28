import domain.Compra;
import domain.MateriaPrima;
import domain.Receita;
import domain.Venda;
import eu.lestard.easydi.EasyDI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import service.BancoDeReceitas;
import service.Estoque;
import service.PersistenceService;
import service.TransacaoService;
import service.interfaces.IBancoDeReceitas;
import service.interfaces.IEstoque;
import service.interfaces.IPersistenceService;
import service.interfaces.ITranscaoService;

import java.io.IOException;
import java.sql.Timestamp;

public class Main extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private TabPane rootTabPane;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Cafeteria");

        initRootLayout();

        addEstoqueTab();
    }

    /**
     * Inicializa o root layout (layout base).
     */
    public void initRootLayout() {
        try {
            // Carrega o root layout do arquivo fxml.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            rootTabPane = (TabPane) rootLayout.getCenter();

            // Mostra a scene (cena) contendo o root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Mostra o person overview dentro do root layout.
     */
    public void addEstoqueTab() {
        try {

            // Carrega o FXML do estoque.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/Estoque.fxml"));
            BorderPane receitasOverview = (BorderPane) loader.load();

            //Cria a tab 1 e define o conteudo do fxml.
            Tab tabEstoque = new Tab();
            tabEstoque.setText("Estoque");
            tabEstoque.setContent(receitasOverview);
            rootTabPane.getTabs().add(tabEstoque);

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

        EasyDI easyDI = new EasyDI();
        easyDI.bindInterface(IEstoque.class, Estoque.class);
        easyDI.bindInterface(IBancoDeReceitas.class, BancoDeReceitas.class);
        easyDI.bindInterface(ITranscaoService.class, TransacaoService.class);
        easyDI.bindInterface(IPersistenceService.class, PersistenceService.class);

        BancoDeReceitas a = easyDI.getInstance(BancoDeReceitas.class);
        Estoque e = easyDI.getInstance(Estoque.class);
        TransacaoService t = easyDI.getInstance(TransacaoService.class);

        t.efetuaCompra(new Compra("Cafe", 5, 4));
        t.efetuaCompra(new Compra("Leite", 10, 6));

        Receita cafecleite = new Receita("cafecomleite");

        cafecleite.addIngrediente(e.getMP("Cafe").getId(), 1);
        cafecleite.addIngrediente(e.getMP("Leite").getId(), 1);

        a.AdicionaReceita(cafecleite);

        t.efetuaVenda(new Venda("cafecomleite", 1, 15));

        var co = t.getCompras(new Timestamp(System.currentTimeMillis()));
        var ce = t.getVendas(new Timestamp(System.currentTimeMillis()));

        launch(args);
    }
}
