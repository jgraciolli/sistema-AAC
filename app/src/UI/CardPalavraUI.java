package UI;

import data.CardPalavra;
import data.Prancha;
import interfaces.ICardPalavra;
import repositories.CardPalavraRepository;
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
        _cardPalavraInterface.visualizarCard();
    }

    private void atualizarCard(){
        _cardPalavraInterface.atualizarCard();
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
