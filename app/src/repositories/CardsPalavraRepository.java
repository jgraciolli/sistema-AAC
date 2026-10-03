package repositories;

import data.CardPalavra;
import data.Prancha;
import interfaces.CardsPalavraInterface;
import utils.FileHelper;
import utils.FilesHelper;

import java.util.ArrayList;
import java.util.List;

public class CardsPalavraRepository implements CardsPalavraInterface {

    FilesHelper _filesHelper;
    FileHelper _fileHelper;
    String _caminho = "cardsPalavras";
    public List<CardPalavra> cardPalavras;

    public CardsPalavraRepository(Prancha prancha){
        _filesHelper = new FilesHelper(_caminho + "\\\\" + prancha.nomeArquivo);
        cardPalavras = buscarCardsDePalavrasLocais();
    }

    @Override
    public List<CardPalavra> carregarTodosCardsDePalavras() {
        return cardPalavras;
    }

    List<CardPalavra> buscarCardsDePalavrasLocais(){
        var listaFiles = _filesHelper.consultarVariosArquivos();
        List<CardPalavra> listaCards = new ArrayList<>();
        for(var file : listaFiles){
            _fileHelper = new FileHelper(_caminho, file.getName());
            List<String> listaString = _fileHelper.consultarConteudoAtualArquivo();
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
