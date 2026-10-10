package data;

import utils.DateTimeHelper;

import javax.smartcardio.Card;
import java.util.ArrayList;
import java.util.List;

public class CardPalavra{
    public int numeroCard;
    public String nomeArquivo;
    public String categoriaCard;
    public String palavraCard;
    public String imagem; //TODO: desenvolver objeto de imagem
    public String corCard; //TODO: desenvolver enumerador de cores
    public int ordem;
    public int numeroDaPrancha;
    public String dataHoraCriacao;

    public CardPalavra(int numeroCard, String categoriaCard, String palavraCard, String imagem, String corCard, int ordem, int numeroDaPrancha){
        this.numeroCard = numeroCard;
        this.nomeArquivo = "CardPalavra_" + numeroCard;
        this.categoriaCard = categoriaCard;
        this.palavraCard = palavraCard;
        this.imagem = imagem;
        this.corCard = corCard;
        this.ordem = ordem;
        this.numeroDaPrancha = numeroDaPrancha;
        this.dataHoraCriacao = new DateTimeHelper().buscarDataHoraAtual();
    }

    public CardPalavra(String categoriaCard, String palavraCard, String imagem, String corCard){
        this.categoriaCard = categoriaCard;
        this.palavraCard = palavraCard;
        this.imagem = imagem;
        this.corCard = corCard;
        this.dataHoraCriacao = new DateTimeHelper().buscarDataHoraAtual();
    }

    public CardPalavra(){};

    public CardPalavra empty(){
        return new CardPalavra(
            categoriaCard = "",
            palavraCard = "",
            imagem = "",
            corCard = ""
        );
    }

    public List<String> converterCardPalavraParaString(CardPalavra cardPalavra){
        List<String> listaCardPalavra = new ArrayList<>();
        listaCardPalavra.add("numeroCard:" + cardPalavra.numeroCard);
        listaCardPalavra.add("categoriaCard:" + cardPalavra.categoriaCard);
        listaCardPalavra.add("palavraCard:" + cardPalavra.palavraCard);
        listaCardPalavra.add("imagem:" + cardPalavra.imagem);
        listaCardPalavra.add("corCard:" + cardPalavra.corCard);
        listaCardPalavra.add("ordem:" + cardPalavra.ordem);
        listaCardPalavra.add("numeroDaPrancha:" + cardPalavra.numeroDaPrancha);
        return listaCardPalavra;
    }

    public CardPalavra converterStringParaCardPalavra(List<String> listString){
        CardPalavra cardPalavra = new CardPalavra().empty();
        for(String s : listString){
            String[] linha = s.split(":", 2);
            String chave = linha[0];
            String valor = linha[linha.length-1];
            cardPalavra = mapearCamposEntidadeCardPalavra(cardPalavra, chave, valor);
        }
        return cardPalavra;
    }

    CardPalavra mapearCamposEntidadeCardPalavra(CardPalavra cardPalavra, String key, String value){
        switch(key){
            case "numeroCard": cardPalavra.numeroCard = Integer.parseInt(value); break;
            case "categoriaCard": cardPalavra.categoriaCard = value; break;
            case "palavraCard": cardPalavra.palavraCard = value; break;
            case "imagem": cardPalavra.imagem = value; break;
            case "corCard": cardPalavra.corCard = value;break;
            case "ordem": cardPalavra.ordem = Integer.parseInt(value);break;
            case "numeroDaPrancha": cardPalavra.numeroDaPrancha = Integer.parseInt(value);break;
        }
        return cardPalavra;
    }
}
