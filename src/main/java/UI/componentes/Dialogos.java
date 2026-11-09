package UI.componentes;

import UI.Navegador;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.List;
import java.util.Optional;

public class Dialogos {

    public static Optional<String> pedirTexto(String titulo, String rotulo, String valorInicial) {
        TextField campo = new TextField(valorInicial);
        Dialog<String> dialog = criar(titulo, ButtonType.OK);
        GridPane grade = grade();
        grade.addRow(0, new Label(rotulo), campo);
        dialog.getDialogPane().setContent(grade);
        exigirPreenchido(dialog, campo);
        dialog.setResultConverter(botao -> botao == ButtonType.OK ? campo.getText().trim() : null);
        campo.requestFocus();
        return dialog.showAndWait();
    }

    /** Retorna [palavra, categoria]. */
    public static Optional<String[]> formularioCard(String titulo, String palavra, String categoria) {
        TextField campoPalavra = new TextField(palavra);
        TextField campoCategoria = new TextField(categoria);
        Dialog<String[]> dialog = criar(titulo, ButtonType.OK);
        GridPane grade = grade();
        grade.addRow(0, new Label("Palavra"), campoPalavra);
        grade.addRow(1, new Label("Categoria"), campoCategoria);
        dialog.getDialogPane().setContent(grade);
        exigirPreenchido(dialog, campoPalavra, campoCategoria);
        dialog.setResultConverter(botao -> botao == ButtonType.OK
                ? new String[]{campoPalavra.getText().trim(), campoCategoria.getText().trim()}
                : null);
        return dialog.showAndWait();
    }

    /** Exibe pares rótulo/valor. Retorna o botão extra se ele for clicado. */
    public static Optional<ButtonType> detalhes(String titulo, List<String[]> campos, ButtonType botaoExtra) {
        Dialog<ButtonType> dialog = criar(titulo, botaoExtra);
        GridPane grade = grade();
        for (int i = 0; i < campos.size(); i++) {
            Label valor = new Label(campos.get(i)[1]);
            valor.getStyleClass().add("dialogo-valor");
            grade.addRow(i, new Label(campos.get(i)[0]), valor);
        }
        dialog.getDialogPane().setContent(grade);
        dialog.setResultConverter(botao -> botao);
        return dialog.showAndWait().filter(botao -> botao == botaoExtra);
    }

    public static boolean confirmar(String titulo, String mensagem) {
        ButtonType excluir = new ButtonType("Excluir", ButtonBar.ButtonData.OK_DONE);
        Dialog<ButtonType> dialog = criar(titulo, excluir);
        Label texto = new Label(mensagem);
        texto.setWrapText(true);
        dialog.getDialogPane().setContent(texto);
        dialog.setResultConverter(botao -> botao);
        return dialog.showAndWait().filter(botao -> botao == excluir).isPresent();
    }

    public static void erro(String mensagem) {
        Dialog<ButtonType> dialog = criar("Erro", null);
        dialog.getDialogPane().setContent(new Label(mensagem));
        dialog.showAndWait();
    }

    private static <T> Dialog<T> criar(String titulo, ButtonType botaoPrincipal) {
        Dialog<T> dialog = new Dialog<>();
        dialog.initOwner(Navegador.stage());
        dialog.setTitle(titulo);
        dialog.setHeaderText(titulo);
        DialogPane painel = dialog.getDialogPane();
        painel.getStylesheets().add(Navegador.ESTILO);
        if (botaoPrincipal != null) painel.getButtonTypes().add(botaoPrincipal);
        painel.getButtonTypes().add(botaoPrincipal == null ? ButtonType.OK : ButtonType.CANCEL);
        return dialog;
    }

    private static GridPane grade() {
        GridPane grade = new GridPane();
        grade.getStyleClass().add("dialogo-grade");
        return grade;
    }

    private static void exigirPreenchido(Dialog<?> dialog, TextField... campos) {
        Node ok = dialog.getDialogPane().lookupButton(ButtonType.OK);
        Runnable validar = () -> {
            boolean algumVazio = false;
            for (TextField campo : campos) algumVazio |= campo.getText().isBlank();
            ok.setDisable(algumVazio);
        };
        for (TextField campo : campos) campo.textProperty().addListener((obs, antes, depois) -> validar.run());
        validar.run();
    }
}
