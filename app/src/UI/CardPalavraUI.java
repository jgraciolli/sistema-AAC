package UI;

import UI.componentes.CaixaTexto;
import data.CardPalavra;
import data.Prancha;
import interfaces.ICardPalavra;
import repositories.CardPalavraRepository;
import repositories.CardsPalavraRepository;
import services.FraseServices;

import java.util.Scanner;

public class CardPalavraUI {
    Scanner scan = new Scanner(System.in);
    CardPalavra _cardPalavra;
    ICardPalavra _cardPalavraInterface;
    Prancha _prancha;
    FraseServices _fraseServices;

    public CardPalavraUI(CardPalavra cardPalavra, Prancha prancha){
        _cardPalavra = cardPalavra;
        _prancha = prancha;
        _cardPalavraInterface = new CardPalavraRepository(_cardPalavra, _prancha);
        renderizarMenuCardPalavra();
    }

    private void renderizarMenuCardPalavra(){
        System.out.println("======================================================================");
        System.out.println("1 - Visualizar informações do card de palavras");
        System.out.println("2 - Atualizar card de palavra");
        System.out.println("3 - Excluir card de palavras");
        System.out.println("4 - adicionar a palavra do card à frase");
        System.out.println("======================================================================");
        selecionarOpcao();
    }

    private void visualizarCard(){
        System.out.println("======================================================================");
        System.out.println("Número do card na prancha: " + _cardPalavra.numeroCard);
        System.out.println("Categoria do card: " + _cardPalavra.categoriaCard); //TODO: UI - TEXTO
        System.out.println("Palavra do card: " + _cardPalavra.palavraCard); //TODO: UI - TEXTO
        System.out.println("Caminho da imagem: " + _cardPalavra.imagem); //TODO: UI - TEXTO
        System.out.println("Cor do card: " + _cardPalavra.corCard); //TODO: UI - TEXTO
        System.out.println("Ordem do card na prancha: " + _cardPalavra.ordem); //TODO: UI - TEXTO
        System.out.println("======================================================================");
    }

    private void atualizarCard(){
        visualizarCard();
        new CaixaTexto("Digite a nova categoria do card de palavra");
        String categoriaCard = CaixaTexto.digitar();
        if(categoriaCard!=null) _cardPalavra.categoriaCard = categoriaCard;
        new CaixaTexto("Digite o novo nome do card de palavra");
        String nomeCard = CaixaTexto.digitar();
        if(nomeCard != null) _cardPalavra.palavraCard = nomeCard;
        _cardPalavraInterface = new CardPalavraRepository(_cardPalavra, _prancha);
        _cardPalavraInterface.atualizarCard();
        visualizarCard();
    }

    private void excluirCard(){
        _cardPalavraInterface.excluirCard();
    }

    private void visualizarHistoricoCard(){
        _cardPalavraInterface.visualizarHistoricoCard();
    }

    void selecionarOpcao(){
        _fraseServices = new FraseServices();
        int opcao = scan.nextInt();
        switch(opcao){
            case 1:visualizarCard();break;
            case 2:atualizarCard();break;
            case 3:excluirCard();break;
            case 4:_fraseServices.adicionarPalavraDoCardAFrase(_cardPalavra.palavraCard);break;
            //case 5: visualizarHistoricoCard();
        }
    }
}
