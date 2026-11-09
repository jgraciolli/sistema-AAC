package UI.componentes;

import javafx.scene.control.Label;

//NOTE: ícones da fonte Material Icons (códigos em MaterialIcons-Regular.codepoints)
public class Icone {
    public static final String ADICIONAR_FOTO = "";
    public static final String REPRODUZIR = "";
    public static final String MAIS_OPCOES = "";
    public static final String VOLTAR = "";

    public static Label criar(String codigo) {
        Label icone = new Label(codigo);
        icone.getStyleClass().add("icone");
        return icone;
    }
}
