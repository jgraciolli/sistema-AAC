package repositories;

import core.database.IDB;
import data.CardPalavra;
import data.Prancha;
import interfaces.ICardsPalavra;
import utils.FileHelper;
import utils.FilesHelper;

import java.util.ArrayList;
import java.util.List;

public class CardsPalavraRepository implements ICardsPalavra {

    FilesHelper _filesHelper;
    IDB db;
    String _caminho = "cardsPalavras";
    public List<CardPalavra> cardPalavras;

    public CardsPalavraRepository(Prancha prancha){
        _filesHelper = new FilesHelper(_caminho + "\\\\" + prancha.nomeArquivo);
        cardPalavras = buscarCardsDePalavrasLocais();
    }

    public CardsPalavraRepository(){
        //TODO: construir construtor para selecionar várias palavras sem ter uma prancha
    }

    @Override
    public List<CardPalavra> carregarTodosCardsDePalavras() {
        return cardPalavras;
    }

    public List<CardPalavra> selecionarCardsDePalavrasEspecificos(){
        //TODO: selecionarMultiplosCards -> esse método que criará a frase
        return List.of();
    }

    List<CardPalavra> buscarCardsDePalavrasLocais(){
        var listaFiles = _filesHelper.consultarVariosArquivos();
        List<CardPalavra> listaCards = new ArrayList<>();
        for(var file : listaFiles){
            db = new FileHelper(_caminho, file.getName());
            List<String> listaString = db.consultar();
            CardPalavra cardPalavra = new CardPalavra().converterStringParaCardPalavra(listaString);
            cardPalavra.nomeArquivo = file.getName();
            cardPalavra.numeroCard = consultarNumeroCard(file.getName());
            listaCards.add(cardPalavra);
        }

        return listaCards;
    }
    int consultarNumeroCard(String nomeArquivo){
        return Integer.parseInt(nomeArquivo.replace("CardPalavra_", ""));
    }
}
