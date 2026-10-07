package UI;

import data.Prancha;
import interfaces.ICardPalavra;

import java.util.Scanner;

public class CardPalavraUI {
    ICardPalavra iCardPalavra;

    Scanner scan = new Scanner(System.in);
    Prancha _prancha;

    private void renderizarMenuCardPalavra(){
        System.out.println("Visualizar informações do card de palavras");
        System.out.println("Atualizar card de palavra");
        System.out.println("Excluir card de palavras");
        System.out.println("Atribuir card de palavras à frase");
    }

    public CardPalavraUI(Prancha prancha){
        _prancha = prancha;
    }

    public void atualizarCard(){

    }

    public void excluirCard(){

    }

    public void visualizarHistoricoCard(){

    }
}
