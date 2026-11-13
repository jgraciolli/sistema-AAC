package services;

import data.CardPalavra;
import data.Prancha;
import interfaces.IFala;
import interfaces.IGeradorImagem;
import repositories.CardPalavraRepository;
import utils.FalaHelper;
import utils.ImageHelper;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class CardPalavraServices {

    IFala _fala = FalaHelper.instancia();
    IGeradorImagem _geradorImagem = new PictogramaServices();

    public void reproduzirPalavra(CardPalavra cardPalavra){
        _fala.falar(cardPalavra.palavraCard);
    }

    public boolean gerarImagemComBaseNaPalavra(CardPalavra cardPalavra, Prancha prancha) throws IOException {
        byte[] imagem = _geradorImagem.gerarImagem(cardPalavra, prancha);

        CardPalavra cardAtual = new CardPalavraRepository(cardPalavra, prancha).visualizarCard();
        boolean cardMudou = !Objects.equals(cardAtual.palavraCard, cardPalavra.palavraCard)
                || !Objects.equals(cardAtual.categoriaCard, cardPalavra.categoriaCard);
        if (cardMudou) return false;

        cardAtual.nomeArquivo = cardPalavra.nomeArquivo;
        String novaImagem = new ImageHelper(prancha.nomeArquivo).gravarImagem(imagem, "pictograma", cardAtual.nomeArquivo);
        aplicarImagem(cardAtual, prancha, novaImagem);
        return true;
    }

    //OBS: (nome_imagem)_(data_atual)_(nome_card).(tipo_imagem)
    public void atribuirImagemAoCard(CardPalavra cardPalavra, Prancha prancha, File imagemOrigem) throws IOException {
        String novaImagem = new ImageHelper(prancha.nomeArquivo).gravarImagem(imagemOrigem, cardPalavra.nomeArquivo);
        aplicarImagem(cardPalavra, prancha, novaImagem);
    }

    private void aplicarImagem(CardPalavra cardPalavra, Prancha prancha, String novaImagem) {
        String imagemAnterior = cardPalavra.imagem;
        cardPalavra.imagem = novaImagem;
        new CardPalavraRepository(cardPalavra, prancha).atualizarCard();
        if (imagemAnterior != null && !imagemAnterior.equals(novaImagem)) {
            ImageHelper.excluirImagem(imagemAnterior);
        }
    }

    public void removerImagemDoCard(CardPalavra cardPalavra, Prancha prancha){
        String imagemAnterior = cardPalavra.imagem;
        cardPalavra.imagem = "";
        new CardPalavraRepository(cardPalavra, prancha).atualizarCard();
        ImageHelper.excluirImagem(imagemAnterior);
    }

    public void reordenarCardsNaPrancha(){
        //TODO: (parametro) receber card que deseja mudar
        //TODO: (parametro) receber novo indice
        //TODO: consultar todos os ARQUIVOS dessa prancha
        //RN: (foreach) para cada o indice de cada card da lista -> indice = indice - 1
        //RN: para o card com indice 0 -> indice = tamanho da lista + 1
        //TODO: (foreach) para cada card da lista gravar em seu respectivo arquivo (utilizar nome do objeto como chave)
    }
}
