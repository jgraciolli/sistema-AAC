package UI.controllers;

import UI.Navegador;
import UI.componentes.CardPalavraComponente;
import UI.componentes.Dialogos;
import UI.componentes.FraseBarra;
import data.CardPalavra;
import data.Prancha;
import interfaces.ICardPalavra;
import interfaces.IPrancha;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import UI.componentes.GradeResponsiva;
import repositories.CardPalavraRepository;
import repositories.CardsPalavraRepository;
import repositories.PranchaRepository;
import services.FraseServices;

import java.util.*;

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

    private final FraseServices _fraseServices = Navegador.fraseServices();
    private final Map<CardPalavra, CardPalavraComponente> componentes = new LinkedHashMap<>();
    private Prancha _prancha;
    private List<CardPalavra> _cards = new ArrayList<>();

    @FXML
    private void initialize() {
        fraseBarra.setAoAlterar(this::atualizarMarcacoes);
    }

    public void carregarPrancha(Prancha prancha) {
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
            cardRepository.criarCard();
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

    private void atualizarMarcacoes() {
        componentes.forEach((card, componente) -> componente.marcarSelecionado(_fraseServices.contemCard(card)));
    }

    private String vazioSeNulo(String valor) {
        return valor == null || valor.isBlank() ? "—" : valor;
    }
}
