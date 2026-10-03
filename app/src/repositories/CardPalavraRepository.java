package repositories;

import data.CardPalavra;
import data.Prancha;
import interfaces.CardPalavraInterface;
import utils.FileHelper;
import utils.FilesHelper;

import java.util.ArrayList;
import java.util.List;

public class CardPalavraRepository implements CardPalavraInterface {

    CardPalavra _cardPalavra;
    FileHelper _fileHelper;
    String _caminho = "cardsPalavras";
    FilesHelper _filesHelper;

    public CardPalavraRepository(CardPalavra cardPalavra, Prancha prancha){
        _caminho = _caminho + "\\\\" + prancha.nomeArquivo;
        _cardPalavra = new CardPalavra(prancha.numeroPrancha, cardPalavra.categoriaCard, cardPalavra.palavraCard, cardPalavra.imagem, cardPalavra.corCard, prancha.numeroPrancha, 1);
        _fileHelper = new FileHelper(_caminho, _cardPalavra.nomeArquivo);

    }

    public void criarCard(){
        if(!_fileHelper.arquivoExiste()){
            _fileHelper.criarArquivo();
            List<String> listaString = new CardPalavra().converterCardPalavraParaString(_cardPalavra);
            _fileHelper.escreverConteudoArquivo(listaString);
        }
    }

    public CardPalavra visualizarCard() {
        List<String> listaStrings = _fileHelper.consultarConteudoAtualArquivo();
        return new CardPalavra().converterStringParaCardPalavra(listaStrings);
    }

    public void atualizarCard(){
        List<String> listaString = new CardPalavra().converterCardPalavraParaString(_cardPalavra);
        _fileHelper.escreverConteudoArquivo(listaString);
    }

    public void excluirCard(){
        _fileHelper.excluirArquivo();
    }

    public List<CardPalavra> visualizarHistoricoCard() {
        List<List<String>> listaLinhas = _fileHelper.consultarConteudoArquivo();
        List<CardPalavra> listaCards = new ArrayList<>();

        for(var l : listaLinhas){
            CardPalavra cardPalavra = new CardPalavra().converterStringParaCardPalavra(l);
            listaCards.add(cardPalavra);
        }
        return listaCards;
    }
}
