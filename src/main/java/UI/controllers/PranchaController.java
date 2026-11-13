package UI.controllers;

import UI.Navegador;
import UI.componentes.CardPalavraComponente;
import UI.componentes.Dialogos;
import UI.componentes.FraseBarra;
import data.CardPalavra;
import data.Prancha;
import interfaces.ICardPalavra;
import interfaces.IPrancha;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import UI.componentes.GradeResponsiva;
import repositories.CardPalavraRepository;
import repositories.CardsPalavraRepository;
import repositories.PranchaRepository;
import javafx.stage.FileChooser;
import services.CardPalavraServices;
import services.FraseServices;
import utils.ImageHelper;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Problema 8: visualizar prancha [R]
// Problema 9: operações da prancha [UD]
// Problema 10: listagem [L] e criação [C] de cards da prancha
// Problemas 11 e 12: visualizar [R], atualizar [U] e excluir [D] card
public class PranchaController implements CardPalavraComponente.Acoes {

    @FXML private Label nomePrancha;
    @FXML private Label detalhesPrancha;
    @FXML private GradeResponsiva gradeCards;
    @FXML private Label mensagemVazia;
    @FXML private FraseBarra fraseBarra;

    private static final ExecutorService GERADOR_IMAGENS = Executors.newSingleThreadExecutor(r -> {
        Thread t = new Thread(r, "gerador-imagens");
        t.setDaemon(true);
        return t;
    });
    private static final Set<String> CARDS_GERANDO_IMAGEM = ConcurrentHashMap.newKeySet();
    private static PranchaController _controllerAtivo;

    private final FraseServices _fraseServices = Navegador.fraseServices();
    private final Map<CardPalavra, CardPalavraComponente> componentes = new LinkedHashMap<>();
    private Prancha _prancha;
    private List<CardPalavra> _cards = new ArrayList<>();

    @FXML
    private void initialize() {
        fraseBarra.setAoAlterar(this::atualizarMarcacoes);
    }

    public void carregarPrancha(Prancha prancha) {
        _controllerAtivo = this;
        _prancha = prancha;
        exibirPrancha();
        carregarCards();
    }

    // ---------- Prancha ----------

    private void exibirPrancha() {
        nomePrancha.setText(_prancha.nomePrancha);
        detalhesPrancha.setText("Prancha " + _prancha.numeroPrancha + "  ·  criada em " + _prancha.dataHoraCriacao);
    }

    @FXML
    private void voltar() {
        Navegador.abrirPranchas();
    }

    @FXML
    private void editarPrancha() {
        Dialogos.pedirTexto("Editar prancha", "Nome da prancha", _prancha.nomePrancha).ifPresent(nome -> {
            Prancha atualizada = new Prancha(_prancha.numeroPrancha, nome, "Prancha_" + _prancha.numeroPrancha);
            IPrancha pranchaRepository = new PranchaRepository(atualizada);
            pranchaRepository.atualizarPrancha();
            _prancha.nomePrancha = nome;
            exibirPrancha();
        });
    }

    @FXML
    private void excluirPrancha() {
        String mensagem = "A prancha \"" + _prancha.nomePrancha + "\" e seus " + _cards.size()
                + " card(s) de palavra serão excluídos. Deseja continuar?";
        if (!Dialogos.confirmar("Excluir prancha", mensagem)) return;
        // A pasta da prancha só é apagada se estiver vazia, então os cards saem primeiro
        for (CardPalavra card : _cards) {
            new CardPalavraRepository(card, _prancha).excluirCard();
        }
        new PranchaRepository(_prancha).excluirPrancha();
        Navegador.abrirPranchas();
    }

    // ---------- Cards ----------

    private void carregarCards() {
        try {
            _cards = new ArrayList<>(new CardsPalavraRepository(_prancha).carregarTodosCardsDePalavras());
        } catch (Exception e) {
            _cards = new ArrayList<>();
        }
        _cards.sort(Comparator.comparingInt(c -> c.numeroCard));

        componentes.clear();
        for (CardPalavra card : _cards) {
            card.numeroDaPrancha = _prancha.numeroPrancha;
            componentes.put(card, new CardPalavraComponente(card, this));
        }
        componentes.forEach((card, componente) -> {
            if (CARDS_GERANDO_IMAGEM.contains(chaveGeracao(_prancha, card))) componente.mostrarGerandoImagem();
        });
        gradeCards.setItens(new ArrayList<>(componentes.values()));
        mensagemVazia.setVisible(_cards.isEmpty());
        mensagemVazia.setManaged(_cards.isEmpty());
        atualizarMarcacoes();
    }

    @FXML
    private void adicionarCard() {
        Dialogos.formularioCard("Adicionar card de palavra", "", "").ifPresent(campos -> {
            CardPalavra novo = new CardPalavra(campos[1], campos[0], "", "");
            ICardPalavra cardRepository = new CardPalavraRepository(novo, _prancha);
            CardPalavra criado = cardRepository.criarCard();
            iniciarGeracaoDeImagem(criado);
            carregarCards();
        });
    }

    @Override
    public void adicionarAFrase(CardPalavra card) {
        fraseBarra.adicionar(card);
    }

    @Override
    public void visualizar(CardPalavra card) {
        ButtonType excluir = new ButtonType("Excluir card", ButtonBar.ButtonData.LEFT);
        List<String[]> campos = List.of(
                new String[]{"Número", String.valueOf(card.numeroCard)},
                new String[]{"Palavra", card.palavraCard},
                new String[]{"Categoria", card.categoriaCard},
                new String[]{"Imagem", vazioSeNulo(card.imagem)},
                new String[]{"Cor", vazioSeNulo(card.corCard)},
                new String[]{"Ordem", String.valueOf(card.ordem)},
                new String[]{"Prancha", _prancha.numeroPrancha + " - " + _prancha.nomePrancha}
        );
        Dialogos.detalhes("Card " + card.numeroCard, campos, excluir).ifPresent(b -> excluir(card));
    }

    @Override
    public void editar(CardPalavra card) {
        Dialogos.formularioCard("Editar card " + card.numeroCard, card.palavraCard, card.categoriaCard).ifPresent(campos -> {
            card.palavraCard = campos[0];
            card.categoriaCard = campos[1];
            ICardPalavra cardRepository = new CardPalavraRepository(card, _prancha);
            cardRepository.atualizarCard();
            carregarCards();
        });
    }

    @Override
    public void excluir(CardPalavra card) {
        if (!Dialogos.confirmar("Excluir card", "O card \"" + card.palavraCard + "\" será excluído. Deseja continuar?")) return;
        ICardPalavra cardRepository = new CardPalavraRepository(card, _prancha);
        cardRepository.excluirCard();
        carregarCards();
    }

    @Override
    public void adicionarImagem(CardPalavra card) {
        FileChooser seletor = new FileChooser();
        seletor.setTitle("Escolher imagem para \"" + card.palavraCard + "\"");
        List<String> filtros = ImageHelper.EXTENSOES.stream().map(ext -> "*." + ext).toList();
        seletor.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imagens", filtros));
        File escolhida = seletor.showOpenDialog(Navegador.stage());
        if (escolhida == null) return;
        try {
            new CardPalavraServices().atribuirImagemAoCard(card, _prancha, escolhida);
        } catch (IOException e) {
            Dialogos.erro("Não foi possível adicionar a imagem: " + e.getMessage());
            return;
        }
        carregarCards();
    }

    @Override
    public void removerImagem(CardPalavra card) {
        if (!Dialogos.confirmar("Remover imagem", "A imagem do card \"" + card.palavraCard + "\" será removida. Deseja continuar?")) return;
        new CardPalavraServices().removerImagemDoCard(card, _prancha);
        carregarCards();
    }

    @Override
    public void gerarImagem(CardPalavra card) {
        iniciarGeracaoDeImagem(card);
        carregarCards();
    }

    private void iniciarGeracaoDeImagem(CardPalavra card) {
        Prancha prancha = _prancha;
        String chave = chaveGeracao(prancha, card);
        if (!CARDS_GERANDO_IMAGEM.add(chave)) return;
        GERADOR_IMAGENS.submit(() -> {
            String erro = null;
            try {
                new CardPalavraServices().gerarImagemComBaseNaPalavra(card, prancha);
            } catch (Exception e) {
                erro = e.getMessage();
            }
            String mensagemErro = erro;
            Platform.runLater(() -> {
                CARDS_GERANDO_IMAGEM.remove(chave);
                PranchaController ativo = _controllerAtivo;
                if (ativo != null && ativo._prancha.nomeArquivo.equals(prancha.nomeArquivo)) ativo.carregarCards();
                if (mensagemErro != null) {
                    Dialogos.erro("Não foi possível gerar a imagem do card \"" + card.palavraCard + "\": " + mensagemErro);
                }
            });
        });
    }

    private static String chaveGeracao(Prancha prancha, CardPalavra card) {
        return prancha.nomeArquivo + "/" + card.nomeArquivo;
    }

    private void atualizarMarcacoes() {
        componentes.forEach((card, componente) -> componente.marcarSelecionado(_fraseServices.contemCard(card)));
    }

    private String vazioSeNulo(String valor) {
        return valor == null || valor.isBlank() ? "—" : valor;
    }
}
