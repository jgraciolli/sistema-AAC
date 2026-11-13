package repositories;

import core.database.IDB;
import data.CardPalavra;
import data.Prancha;
import interfaces.ICardPalavra;
import utils.FileHelper;
import utils.FilesHelper;
import utils.ImageHelper;

import java.util.ArrayList;
import java.util.List;

public class CardPalavraRepository implements ICardPalavra {

    CardPalavra _cardPalavra;
    Prancha _prancha;
    IDB db;
    String _caminho = "cardsPalavras";
    FilesHelper _filesHelper;


    public CardPalavraRepository(CardPalavra cardPalavra, Prancha prancha){
        _cardPalavra = cardPalavra;
        _prancha = prancha;
        _caminho = _caminho + "\\\\" + prancha.nomeArquivo;
        db = new FileHelper(_caminho, _cardPalavra.nomeArquivo);
    }

    public CardPalavra criarCard(){
        _filesHelper = new FilesHelper(_caminho);
        int numeroCard = _filesHelper.definirNumeroObjetoAutomaticamente();
        _cardPalavra = new CardPalavra(numeroCard, _cardPalavra.categoriaCard, _cardPalavra.palavraCard, _cardPalavra.imagem, _cardPalavra.corCard, _cardPalavra.numeroCard, _prancha.numeroPrancha);
        db = new FileHelper(_caminho, _cardPalavra.nomeArquivo);
        if(db.existe()){
            db.criar();
            List<String> listaString = new CardPalavra().converterCardPalavraParaString(_cardPalavra);
            db.escrever(listaString);
        }
        return _cardPalavra;
    }

    public CardPalavra visualizarCard() {
        List<String> listaStrings = db.consultar();
        return new CardPalavra().converterStringParaCardPalavra(listaStrings);
    }

    public void atualizarCard(){
        List<String> listaString = new CardPalavra().converterCardPalavraParaString(_cardPalavra);
        db.escrever(listaString);
    }

    public void excluirCard(){
        db.excluir();
        ImageHelper.excluirImagem(_cardPalavra.imagem);
    }

    public List<CardPalavra> visualizarHistoricoCard() {
        List<List<String>> listaLinhas = db.consultarHistorico();
        List<CardPalavra> listaCards = new ArrayList<>();

        for(var l : listaLinhas){
            CardPalavra cardPalavra = new CardPalavra().converterStringParaCardPalavra(l);
            listaCards.add(cardPalavra);
        }
        return listaCards;
    }
}
