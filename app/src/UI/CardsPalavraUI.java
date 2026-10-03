package UI;

import UI.componentes.CaixaSelecao;
import UI.componentes.CaixaTexto;
import data.CardPalavra;
import data.Prancha;
import interfaces.CardPalavraInterface;
import interfaces.CardsPalavraInterface;
import repositories.CardPalavraRepository;
import repositories.CardsPalavraRepository;

import java.util.List;

public class CardsPalavraUI {
    CardsPalavraInterface _cardsPalavrasInterface;
    Prancha _prancha;
    CardPalavraInterface _cardPalavrasInterface;
    List<CardPalavra> _listaCardPalavra;

    CardsPalavraUI(Prancha prancha){
        _prancha = prancha;
        _cardsPalavrasInterface = new CardsPalavraRepository(_prancha);
    }


    public void renderizarTelaCard(){
        System.out.println("MENU DE CARDS");
        System.out.println("1 - Selecionar card de palavra");
        System.out.println("2 - Criar novo card de palavra");
        selecionarOpcao();
    }

    public void consultarTodosCardsPalavras(){

        try {
            _listaCardPalavra = _cardsPalavrasInterface.carregarTodosCardsDePalavras();
            for(var cardPalavra : _listaCardPalavra){
                System.out.println("Categoria do card: " + cardPalavra.numeroCard); //TODO: UI - TEXTO
                System.out.println("Palavra do card: " + cardPalavra.palavraCard); //TODO: UI - TEXTO
                System.out.println("Caminho da imagem: " + cardPalavra.imagem); //TODO: UI - TEXTO
                System.out.println("Cor do card: " + cardPalavra.corCard); //TODO: UI - TEXTO
                System.out.println("Ordem do card na prancha: " + cardPalavra.ordem); //TODO: UI - TEXTO
            }
            _selecionarCard();
        } catch (Exception e) {
            System.out.println("Erro ao consultar cards de palavra");
        }
    }

    public void _selecionarCard(){
        int numero = new CaixaSelecao().selecionar();
        _cardPalavrasInterface = new CardPalavraRepository(_listaCardPalavra.get(numero), _prancha);
    }

    public void criarCard(){
        new CaixaTexto("Digite o nome do card de palavra");
        String categoriaCard = CaixaTexto.digitar();
        new CaixaTexto("Digite a categoria do card de palavra");
        String nomeCard = CaixaTexto.digitar();
        var cardPalavra = new CardPalavra(
                categoriaCard,
                nomeCard,
                "caminho_imaginario_1",
                "preto"
        );
        _cardPalavrasInterface = new CardPalavraRepository(cardPalavra, _prancha);
        _cardPalavrasInterface.criarCard();
    }


    private void selecionarOpcao(){
        new CaixaSelecao();
        int opcao = CaixaSelecao.selecionar();
        switch(opcao){
            case 1: selecionarOpcao();
            case 2: criarCard();
        }
    }
}
