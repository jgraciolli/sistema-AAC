package services;

import data.CardPalavra;
import data.Prancha;
import interfaces.IFala;
import repositories.CardPalavraRepository;
import utils.FalaHelper;
import utils.ImageHelper;

import java.io.File;
import java.io.IOException;

public class CardPalavraServices {

    IFala _fala = FalaHelper.instancia();

    public void reproduzirPalavra(CardPalavra cardPalavra){
        _fala.falar(cardPalavra.palavraCard);
    }
    public void gerarImagensComBaseNasPalavras(){
        //DOCS: pesquisar IA capaz de gerar imagens com base em palavras
    }

    //OBS: (nome_imagem)_(data_atual)_(nome_card).(tipo_imagem)
    public void atribuirImagemAoCard(CardPalavra cardPalavra, Prancha prancha, File imagemOrigem) throws IOException {
        String imagemAnterior = cardPalavra.imagem;
        String novaImagem = new ImageHelper(prancha.nomeArquivo).gravarImagem(imagemOrigem, cardPalavra.nomeArquivo);
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
