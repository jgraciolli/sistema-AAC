package data;

import utils.DateTimeHelper;

import java.util.ArrayList;
import java.util.List;

public class Frase {
    public List<CardPalavra> cardsPalavra = new ArrayList<>();
    public String dataHoraCriacao;

    public Frase() {
        this.dataHoraCriacao = new DateTimeHelper().buscarDataHoraAtual();
    }

    public String montarTexto() {
        List<String> palavras = new ArrayList<>();
        for (CardPalavra card : cardsPalavra) {
            palavras.add(card.palavraCard);
        }
        return String.join(" ", palavras);
    }

    //NOTE: cada card da frase vira uma linha "card:numeroDaPrancha;numeroCard;palavraCard"
    public List<String> converterFraseParaString(Frase frase) {
        List<String> listaFrase = new ArrayList<>();
        listaFrase.add("data:" + frase.dataHoraCriacao);
        for (CardPalavra card : frase.cardsPalavra) {
            listaFrase.add("card:" + card.numeroDaPrancha + ";" + card.numeroCard + ";" + card.palavraCard);
        }
        return listaFrase;
    }

    public Frase converterStringParaFrase(List<String> listaString) {
        Frase frase = new Frase();
        for (String s : listaString) {
            String[] linha = s.split(":", 2);
            String chave = linha[0];
            String valor = linha[linha.length - 1];
            switch (chave) {
                case "data": frase.dataHoraCriacao = valor; break;
                case "card": frase.cardsPalavra.add(converterStringParaCard(valor)); break;
            }
        }
        return frase;
    }

    CardPalavra converterStringParaCard(String valor) {
        String[] campos = valor.split(";", 3);
        CardPalavra card = new CardPalavra();
        card.numeroDaPrancha = Integer.parseInt(campos[0]);
        card.numeroCard = Integer.parseInt(campos[1]);
        card.palavraCard = campos[2];
        return card;
    }
}
