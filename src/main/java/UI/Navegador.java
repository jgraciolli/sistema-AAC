package UI;

import UI.controllers.PranchaController;
import data.Prancha;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import services.FraseServices;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Objects;

public class Navegador {

    public static final String ESTILO = Objects.requireNonNull(Navegador.class.getResource("/UI/styles/app.css")).toExternalForm();

    private static Stage _stage;
    private static Scene _scene;
    //NOTE: a frase é compartilhada entre todas as telas
    private static FraseServices _fraseServices;

    public static void iniciar(Stage stage) {
        _stage = stage;
        _fraseServices = new FraseServices();
        _scene = new Scene(new Pane(), 1280, 800);
        _scene.getStylesheets().add(ESTILO);

        stage.setTitle("Sistema AAC Calopsito");
        stage.setScene(_scene);
        stage.setMinWidth(900);
        stage.setMinHeight(600);
        stage.setMaximized(true);
        abrirPranchas();
        stage.show();
    }

    public static void abrirPranchas() {
        _scene.setRoot(carregar("/UI/views/PranchasView.fxml").getRoot());
    }

    public static void abrirPrancha(Prancha prancha) {
        FXMLLoader loader = carregar("/UI/views/PranchaView.fxml");
        PranchaController controller = loader.getController();
        controller.carregarPrancha(prancha);
        _scene.setRoot(loader.getRoot());
    }

    public static Stage stage() {
        return _stage;
    }

    public static FraseServices fraseServices() {
        return _fraseServices;
    }

    private static FXMLLoader carregar(String caminho) {
        FXMLLoader loader = new FXMLLoader(Navegador.class.getResource(caminho));
        try {
            Parent ignored = loader.load();
        } catch (IOException e) {
            throw new UncheckedIOException("Erro ao carregar a tela " + caminho, e);
        }
        return loader;
    }
}
