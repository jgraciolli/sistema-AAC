package interfaces;

import data.CardPalavra;

import java.util.List;

public interface ICardPalavra {
    CardPalavra visualizarCard();
    List<CardPalavra> visualizarHistoricoCard();
    CardPalavra criarCard();
    void atualizarCard();
    void excluirCard();
}
