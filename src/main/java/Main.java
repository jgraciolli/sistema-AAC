import UI.App;
import javafx.application.Application;

public class Main {
    public static void main(String[] args) {
        // Main não estende Application de propósito: assim o app roda direto pelo
        // classpath (IDE ou java -cp) sem precisar de module-info.
        Application.launch(App.class, args);
    }
}
