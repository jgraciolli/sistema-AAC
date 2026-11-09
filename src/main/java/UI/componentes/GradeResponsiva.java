package UI.componentes;

import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

// Grade que escolhe o número de colunas pela largura disponível,
// garantindo que cada item tenha pelo menos "larguraMinima".
public class GradeResponsiva extends GridPane {

    private double larguraMinima = 200;
    private List<? extends Node> itens = new ArrayList<>();
    private int colunas = -1;

    public GradeResponsiva() {
        getStyleClass().add("grade");
        widthProperty().addListener((obs, antes, depois) -> reorganizar(false));
    }

    public double getLarguraMinima() {
        return larguraMinima;
    }

    public void setLarguraMinima(double larguraMinima) {
        this.larguraMinima = larguraMinima;
        reorganizar(true);
    }

    public void setItens(List<? extends Node> itens) {
        this.itens = itens;
        reorganizar(true);
    }

    private void reorganizar(boolean forcar) {
        int novasColunas = Math.max(1, (int) ((getWidth() + getHgap()) / (larguraMinima + getHgap())));
        if (!forcar && novasColunas == colunas) return;
        colunas = novasColunas;

        getColumnConstraints().clear();
        for (int i = 0; i < colunas; i++) {
            ColumnConstraints coluna = new ColumnConstraints();
            coluna.setPercentWidth(100.0 / colunas);
            getColumnConstraints().add(coluna);
        }
        getChildren().clear();
        for (int i = 0; i < itens.size(); i++) {
            add(itens.get(i), i % colunas, i / colunas);
        }
    }
}
