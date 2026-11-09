package UI.controllers;

import UI.Navegador;
import UI.componentes.Dialogos;
import UI.componentes.PranchaTile;
import data.Prancha;
import interfaces.IPrancha;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import UI.componentes.GradeResponsiva;
import repositories.PranchaRepository;
import repositories.PranchasRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// Problema 7: listagem [L] e criação [C] de pranchas
public class PranchasController {

    @FXML private GradeResponsiva gradePranchas;
    @FXML private Label mensagemVazia;

    @FXML
    private void initialize() {
        carregarPranchas();
    }

    @FXML
    private void adicionarPrancha() {
        Dialogos.pedirTexto("Adicionar prancha", "Nome da prancha", "").ifPresent(nome -> {
            IPrancha pranchaRepository = new PranchaRepository(new Prancha(nome));
            pranchaRepository.criarPrancha();
            carregarPranchas();
        });
    }

    private void carregarPranchas() {
        List<Prancha> pranchas = new ArrayList<>(new PranchasRepository().carregarTodasPranchas());
        pranchas.sort(Comparator.comparingInt(p -> p.numeroPrancha));

        List<PranchaTile> tiles = new ArrayList<>();
        for (Prancha prancha : pranchas) {
            tiles.add(new PranchaTile(prancha, () -> Navegador.abrirPrancha(prancha)));
        }
        gradePranchas.setItens(tiles);
        mensagemVazia.setVisible(pranchas.isEmpty());
        mensagemVazia.setManaged(pranchas.isEmpty());
    }
}
