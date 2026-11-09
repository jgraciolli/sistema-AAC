package UI;

import UI.componentes.CaixaSelecao;
import UI.componentes.CaixaTexto;
import data.CardPalavra;
import data.Prancha;
import interfaces.ICardPalavra;
import interfaces.ICardsPalavra;
import repositories.CardPalavraRepository;
import repositories.CardsPalavraRepository;

import java.util.List;

public class CardsPalavraUI {
    ICardsPalavra _cardsPalavrasInterface;
    Prancha _prancha;
    ICardPalavra _cardPalavrasInterface;
    List<CardPalavra> _listaCardPalavra;
    CardPalavraUI _telaCardPalavra;
    CardPalavra _cardPalavra;

    CardsPalavraUI(Prancha prancha){
        _prancha = prancha;
        consultarTodosCardsPalavras();
        _renderizarTelaCard();
    }


    public void _renderizarTelaCard(){
        System.out.println("MENU DE CARDS");
        System.out.println("1 - Selecionar card de palavra");
        System.out.println("2 - Criar novo card de palavra");
        selecionarOpcao();
    }

    public void consultarTodosCardsPalavras(){
        try {
            _cardsPalavrasInterface = new CardsPalavraRepository(_prancha);
            _listaCardPalavra = _cardsPalavrasInterface.carregarTodosCardsDePalavras();
            if(_listaCardPalavra.isEmpty()){
                System.out.println("Nenhuma card de palavra nessa prancha"); //TODO: UI - DIALOG BOX
                return;
            };
            for(var cardPalavra : _listaCardPalavra){
                System.out.println("======================================================================");
                System.out.println("Número card de palavra: " + cardPalavra.numeroCard);
                System.out.println("Categoria do card: " + cardPalavra.categoriaCard); //TODO: UI - TEXTO
                System.out.println("Palavra do card: " + cardPalavra.palavraCard); //TODO: UI - TEXTO
                System.out.println("Caminho da imagem: " + cardPalavra.imagem); //TODO: UI - TEXTO
                System.out.println("Cor do card: " + cardPalavra.corCard); //TODO: UI - TEXTO
                System.out.println("Ordem do card na prancha: " + cardPalavra.ordem); //TODO: UI - TEXTO
                System.out.println("======================================================================");
            }
        } catch (Exception e) {
            System.out.println("Erro ao consultar cards de palavra");
        }
    }

    public void _selecionarCard(){
        new CaixaSelecao("Qual card deseja selecionar:");
        int numero = CaixaSelecao.selecionar();
        _cardPalavra = _listaCardPalavra.get(numero-1);
        _cardPalavrasInterface = new CardPalavraRepository(_cardPalavra, _prancha);
        _telaCardPalavra = new CardPalavraUI(_cardPalavra, _prancha);
    }

    public void criarCard(){
        new CaixaTexto("Digite a categoria do card de palavra");
        String categoriaCard = CaixaTexto.digitar();
        new CaixaTexto("Digite o nome do card de palavra");
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
        new CaixaSelecao("Seleciona uma opção:");
        int opcao = CaixaSelecao.selecionar();
        switch(opcao){
            case 1: _selecionarCard();break;
            case 2: criarCard();break;
        }
    }
}
