package UI.componentes;

import data.Prancha;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

// Figma: "Frame 14" (bloco da prancha na listagem de pranchas)
public class PranchaTile extends VBox {

    public PranchaTile(Prancha prancha, Runnable aoSelecionar) {
        getStyleClass().add("prancha-tile");
        Label numero = new Label("Prancha " + prancha.numeroPrancha);
        Label nome = new Label(prancha.nomePrancha);
        numero.getStyleClass().add("texto-grande");
        nome.getStyleClass().add("texto-grande");
        nome.setWrapText(true);
        nome.setTextAlignment(TextAlignment.CENTER);
        getChildren().addAll(numero, nome);
        setOnMouseClicked(e -> aoSelecionar.run());
    }
}
