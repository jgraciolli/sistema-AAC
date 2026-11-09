package UI;

import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        Font.loadFont(App.class.getResourceAsStream("/UI/fonts/Inter-Regular.ttf"), 14);
        Font.loadFont(App.class.getResourceAsStream("/UI/fonts/MaterialIcons-Regular.ttf"), 14);
        Navegador.iniciar(stage);
    }
}
