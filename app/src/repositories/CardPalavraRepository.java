package repositories;

import core.database.IDB;
import data.CardPalavra;
import data.Prancha;
import interfaces.ICardPalavra;
import utils.FileHelper;

import java.util.ArrayList;
import java.util.List;

public class CardPalavraRepository implements ICardPalavra {

    CardPalavra _cardPalavra;
    IDB db;
    String _caminho = "cardsPalavras";

    public CardPalavraRepository(CardPalavra cardPalavra, Prancha prancha){
        _caminho = _caminho + "\\\\" + prancha.nomeArquivo;
        _cardPalavra = new CardPalavra(prancha.numeroPrancha, cardPalavra.categoriaCard, cardPalavra.palavraCard, cardPalavra.imagem, cardPalavra.corCard, prancha.numeroPrancha, 1);
        db = new FileHelper(_caminho, _cardPalavra.nomeArquivo);
    }

    public void criarCard(){
        if(db.existe()){
            db.criar();
            List<String> listaString = new CardPalavra().converterCardPalavraParaString(_cardPalavra);
            db.escrever(listaString);
        }
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
