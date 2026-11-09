package UI.componentes;

import UI.Navegador;
import data.CardPalavra;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import services.FraseServices;

import java.util.List;

// Figma: "Frame 13" (faixa da frase no rodapé). Compartilhada entre as telas.
public class FraseBarra extends HBox {

    private final FraseServices _fraseServices = Navegador.fraseServices();
    private final HBox palavras = new HBox();
    private final Label status = new Label();
    private Runnable aoAlterar = () -> {};

    public FraseBarra() {
        getStyleClass().add("frase-barra");
        palavras.getStyleClass().add("frase-palavras");

        ScrollPane rolagem = new ScrollPane(palavras);
        rolagem.getStyleClass().add("frase-rolagem");
        rolagem.setFitToHeight(true);
        rolagem.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        HBox.setHgrow(rolagem, Priority.ALWAYS);

        Button reproduzir = new Button("Reproduzir frase");
        reproduzir.getStyleClass().add("botao-primario");
        reproduzir.setOnAction(e -> _fraseServices.reproduzirFrase());
        Button salvar = new Button("Salvar frase");
        Button excluir = new Button("Excluir frase");
        salvar.getStyleClass().add("botao-primario");
        excluir.getStyleClass().add("botao-primario");
        salvar.setOnAction(e -> salvar());
        excluir.setOnAction(e -> excluir());
        status.getStyleClass().add("texto-pequeno");

        getChildren().addAll(rolagem, status, reproduzir, salvar, excluir);
        atualizar();
    }

    public void setAoAlterar(Runnable aoAlterar) {
        this.aoAlterar = aoAlterar;
    }

    public void adicionar(CardPalavra card) {
        _fraseServices.adicionarPalavraDoCardAFrase(card);
        alterou();
    }

    public void atualizar() {
        palavras.getChildren().clear();
        List<CardPalavra> cards = _fraseServices.visualizarFraseAtual();
        if (cards.isEmpty()) {
            Label vazio = new Label("Clique em um card de palavra para montar a frase");
            vazio.getStyleClass().add("frase-vazia");
            palavras.getChildren().add(vazio);
            return;
        }
        for (int i = 0; i < cards.size(); i++) {
            int posicao = i;
            Button palavra = new Button(cards.get(i).palavraCard);
            palavra.getStyleClass().add("frase-palavra");
            palavra.setTooltip(new Tooltip("Clique para remover da frase"));
            palavra.setOnAction(e -> {
                _fraseServices.removerPalavraDaFrase(posicao);
                alterou();
            });
            palavras.getChildren().add(palavra);
        }
    }

    private void salvar() {
        _fraseServices.salvarFrase();
        status.setText("Frase salva");
    }

    private void excluir() {
        if (!Dialogos.confirmar("Excluir frase", "A frase atual será apagada. Deseja continuar?")) return;
        _fraseServices.excluirFrase();
        alterou();
        status.setText("Frase excluída");
    }

    private void alterou() {
        status.setText("");
        atualizar();
        aoAlterar.run();
    }
}
