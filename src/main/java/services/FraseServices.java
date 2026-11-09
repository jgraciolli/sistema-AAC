package services;

import data.CardPalavra;
import data.Frase;
import interfaces.IFrase;
import repositories.FraseRepository;
import interfaces.IFala;
import utils.FalaHelper;

import java.util.List;

public class FraseServices {
    //OBS: Construir frase -> Adicionar palavra do card a frase
    //NOTE: uma única instância é compartilhada entre as telas (ver UI.Navegador)

    IFrase _fraseRepository;
    IFala _fala = FalaHelper.instancia();
    Frase _fraseAtual;

    public FraseServices() {
        _fraseRepository = new FraseRepository();
        _fraseAtual = _fraseRepository.visualizarFrase();
    }

    public void adicionarPalavraDoCardAFrase(CardPalavra cardPalavra) {
        _fraseAtual.cardsPalavra.add(cardPalavra);
    }

    public void removerPalavraDaFrase(int posicao) {
        _fraseAtual.cardsPalavra.remove(posicao);
    }

    public List<CardPalavra> visualizarFraseAtual() {
        return _fraseAtual.cardsPalavra;
    }

    public boolean contemCard(CardPalavra cardPalavra) {
        for (CardPalavra card : _fraseAtual.cardsPalavra) {
            if (card.numeroDaPrancha == cardPalavra.numeroDaPrancha && card.numeroCard == cardPalavra.numeroCard) {
                return true;
            }
        }
        return false;
    }

    public void salvarFrase() {
        _fraseRepository.salvarFrase(_fraseAtual);
    }

    public void excluirFrase() {
        _fraseRepository.excluirFrase();
        _fraseAtual = new Frase();
    }

    public void reproduzirFrase(){
        _fala.falar(_fraseAtual.montarTexto());
    }

    //TODO: construir um repositório apenas para imagens
}
