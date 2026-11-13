package interfaces;

import data.CardPalavra;
import data.Prancha;

import java.io.IOException;

public interface IGeradorImagem {
    byte[] gerarImagem(CardPalavra cardPalavra, Prancha prancha) throws IOException;
}
