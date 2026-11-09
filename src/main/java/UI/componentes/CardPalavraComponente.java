package UI.componentes;

import data.CardPalavra;
import services.CardPalavraServices;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.File;

// Figma: componente "Card de palavra"
public class CardPalavraComponente extends VBox {

    public interface Acoes {
        void adicionarAFrase(CardPalavra card);
        void visualizar(CardPalavra card);
        void editar(CardPalavra card);
        void excluir(CardPalavra card);
    }

    private final Region marcaSelecao = new Region();

    public CardPalavraComponente(CardPalavra card, Acoes acoes) {
        getStyleClass().add("card-palavra");

        // Topo: seleção, título, ações e menu
        StackPane selecao = new StackPane(marcaSelecao);
        selecao.getStyleClass().add("card-selecao");
        marcaSelecao.getStyleClass().add("card-selecao-marca");

        Label titulo = new Label("Card " + card.numeroCard);
        titulo.getStyleClass().add("texto-grande");
        StackPane caixaTitulo = new StackPane(titulo);
        caixaTitulo.getStyleClass().add("card-titulo");

        Button adicionarFoto = botaoIcone(Icone.ADICIONAR_FOTO, "Adicionar imagem (versão 0.3.0)");
        Button reproduzir = botaoIcone(Icone.REPRODUZIR, "Reproduzir palavra");
        reproduzir.setOnAction(e -> new CardPalavraServices().reproduzirPalavra(card));
        HBox botoes = new HBox(adicionarFoto, reproduzir);
        botoes.getStyleClass().add("card-botoes");
        HBox.setHgrow(botoes, Priority.ALWAYS);

        MenuItem visualizar = new MenuItem("Visualizar card");
        MenuItem editar = new MenuItem("Editar card");
        MenuItem excluir = new MenuItem("Excluir card");
        visualizar.setOnAction(e -> acoes.visualizar(card));
        editar.setOnAction(e -> acoes.editar(card));
        excluir.setOnAction(e -> acoes.excluir(card));
        ContextMenu opcoes = new ContextMenu(visualizar, editar, excluir);
        Button menu = new Button(null, Icone.criar(Icone.MAIS_OPCOES));
        menu.getStyleClass().add("card-menu");
        menu.setOnAction(e -> opcoes.show(menu, Side.BOTTOM, 0, 0));

        HBox topo = new HBox(selecao, caixaTitulo, botoes, menu);
        topo.getStyleClass().add("card-topo");

        // Imagem
        StackPane areaImagem = new StackPane();
        areaImagem.getStyleClass().add("card-imagem");
        Node imagem = carregarImagem(card.imagem);
        if (imagem != null) areaImagem.getChildren().add(imagem);

        // Palavra e categoria
        Label palavra = new Label(card.palavraCard);
        palavra.getStyleClass().add("texto-grande");
        StackPane caixaPalavra = new StackPane(palavra);
        caixaPalavra.getStyleClass().add("card-palavra-texto");

        Label categoria = new Label(card.categoriaCard);
        categoria.getStyleClass().add("texto-pequeno");
        HBox caixaCategoria = new HBox(categoria);
        caixaCategoria.getStyleClass().add("card-categoria");

        getChildren().addAll(topo, areaImagem, caixaPalavra, caixaCategoria);

        // Clicar no card (fora dos botões) atribui a palavra à frase
        setOnMouseClicked(e -> {
            if (!cliqueEmBotao(e.getPickResult().getIntersectedNode())) acoes.adicionarAFrase(card);
        });
    }

    public void marcarSelecionado(boolean selecionado) {
        marcaSelecao.setVisible(selecionado);
    }

    private Button botaoIcone(String icone, String dica) {
        Button botao = new Button(null, Icone.criar(icone));
        botao.getStyleClass().add("card-botao-icone");
        botao.setTooltip(new Tooltip(dica));
        return botao;
    }

    private Node carregarImagem(String caminho) {
        if (caminho == null || caminho.isBlank() || !new File(caminho).isFile()) return null;
        ImageView view = new ImageView(new Image(new File(caminho).toURI().toString(), 120, 120, true, true));
        view.setFitWidth(120);
        view.setFitHeight(120);
        view.setPreserveRatio(true);
        return view;
    }

    private boolean cliqueEmBotao(Node node) {
        while (node != null && node != this) {
            if (node instanceof ButtonBase) return true;
            node = node.getParent();
        }
        return false;
    }
}
