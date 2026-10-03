package interfaces;

import data.CardPalavra;

import java.util.List;

public interface CardPalavraInterface {
    CardPalavra visualizarCard();
    List<CardPalavra> visualizarHistoricoCard();
    void criarCard();
    void atualizarCard();
    void excluirCard();
}
