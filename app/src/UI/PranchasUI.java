package UI;

import UI.componentes.CaixaSelecao;
import UI.componentes.CaixaTexto;
import data.Prancha;
import interfaces.PranchaInterface;
import interfaces.PranchasInterface;
import repositories.PranchaRepository;
import repositories.PranchasRepository;

import java.util.List;
import java.util.Scanner;

public class PranchasUI {

    Scanner scan;
    PranchasInterface _pranchasInterface;
    List<Prancha> _pranchas;
    PranchaInterface _pranchaInterface;
    PranchaUI _pranchaUI;

    public PranchasUI(){
        _pranchasInterface = new PranchasRepository();
        scan = new Scanner(System.in);
        carregarTodasPranchas();
        renderizarTelaInicial();
    }


    public void renderizarTelaInicial() {
        System.out.println("1 - Selecionar prancha"); //TODO: UI - BOTÃO
        System.out.println("2 - Criar nova prancha"); //TODO: UI - BOTÃO
        //System.out.println("5 - Visualizar histórico da Prancha"); //TODO: UI - BOTÂO
        selecionarOpcao();
    }

    void carregarTodasPranchas(){
        _pranchas = _pranchasInterface.carregarTodasPranchas();
        if(_pranchas.isEmpty()){
            System.out.println("Nenhuma prancha inserida"); //TODO: UI - DIALOG BOX
            return;
        };
        for(Prancha prancha : _pranchas){
            visualizarPrancha(prancha);
        }
    }

    void visualizarPrancha(Prancha prancha){
        System.out.println("======================================================================");
        System.out.println("número da prancha:" + prancha.numeroPrancha); //TODO: UI - Texto
        System.out.println("nome prancha: " + prancha.nomePrancha); //TODO: UI - Texto
        System.out.println("data de criação prancha: " + prancha.dataHoraCriacao); //TODO: UI - Texto
        System.out.println("======================================================================");
    }

    void _selecionarPrancha(){
        new CaixaSelecao("Qual a prancha que deseja selecionar?");
        int numero = CaixaSelecao.selecionar();
        var prancha = _pranchas.get(numero);
        _pranchaUI = new PranchaUI(prancha);
    }

    void _criarPrancha(){
        new CaixaTexto("Digite o nome da prancha");
        String nomePrancha = CaixaTexto.digitar();
        Prancha prancha = new Prancha(nomePrancha);
        _pranchaInterface = new PranchaRepository(prancha);
        _pranchaInterface.criarPrancha(); //TODO: UI - BOTÃO
    }

    //NOTE: função temporária
    void selecionarOpcao(){
        new CaixaSelecao();
        int opcao = CaixaSelecao.selecionar();
        switch (opcao){
            case 1: _selecionarPrancha();
            case 2: _criarPrancha();
        }
    }

}
