package UI;

import UI.componentes.CaixaSelecao;
import UI.componentes.CaixaTexto;
import data.Prancha;
import interfaces.IPrancha;
import repositories.PranchaRepository;

import java.util.Scanner;

public class PranchaUI{

    Scanner scan = new Scanner(System.in);;
    Prancha _prancha;
    IPrancha _I_prancha;
    CardsPalavraUI _telaCardPalavra;

    public PranchaUI(Prancha prancha){
        _prancha = prancha;
        _I_prancha = new PranchaRepository(_prancha);
        _renderizarMenuPrancha();
    }

    void _renderizarMenuPrancha(){
        System.out.println("O QUE DESEJA FAZER?"); //TODO: UI - TEXTO
        System.out.println("1 - Visualizar informações da prancha"); //TODO: UI - TEXTO
        System.out.println("2 - Atualizar Prancha"); //TODO: UI - BOTÃO
        System.out.println("3 - Excluir Prancha"); //TODO: UI - BOTÃO
        System.out.println("4 - Visualizar Cards da Prancha");
        //System.out.println("6 - Visualizar histórico da prancha"); //TODO: UI - BOTÃO
        selecionarOpcao();
    }

    void visualizarPrancha(){
        System.out.println("======================================================================");
        System.out.println("número da prancha:" + _prancha.numeroPrancha); //TODO: UI - Texto
        System.out.println("nome prancha: " + _prancha.nomePrancha); //TODO: UI - Texto
        System.out.println("data de criação prancha: " + _prancha.dataHoraCriacao); //TODO: UI - Texto
        System.out.println("======================================================================");
    }

    void atualizarPrancha(){
        //TODO: informar quais campos podem ser editados
        //TODO: percorrer entidade em busca do nome das propriedades
        new CaixaTexto("Digite o novo nome da prancha");
        String nomePrancha = CaixaTexto.digitar(); //TODO: UI - TEXT BOX
        if(nomePrancha==null) nomePrancha = _prancha.nomePrancha;
        Prancha novaPrancha = new Prancha(nomePrancha);
        _I_prancha = new PranchaRepository(novaPrancha);

        _I_prancha.atualizarPrancha(); //TODO: UI - BOTÃO
    }

    void excluirPrancha(){
        _I_prancha.excluirPrancha(); //TODO: UI - BOTÃO
    }

    void visualizarHistoricoPrancha(){
        _I_prancha.visualizarHistoricoPrancha();
    }

    private void selecionarOpcao(){
        new CaixaSelecao();
        int opcao = CaixaSelecao.selecionar();

        switch(opcao){
            case 1: visualizarPrancha();break;
            case 2: atualizarPrancha();break;
            case 3: excluirPrancha();break;
            case 4: _telaCardPalavra = new CardsPalavraUI(_prancha);break;
            //case 6: telaPrancha.visualizarHistoricoPrancha();
        }
    }
}
