package UI;

import data.Prancha;
import interfaces.PranchaInterface;
import interfaces.PranchasInterface;
import repositories.PranchaRepository;
import repositories.PranchasRepository;

import java.util.List;
import java.util.Scanner;

public class PranchaUI{

    Scanner scan;

    public PranchaUI(){
        scan = new Scanner(System.in);
    }

    PranchaInterface _pranchaInterface;
    Prancha _pranchaSelecionada;

            PranchasInterface _pranchasInterface = new PranchasRepository();
    void carregarTodasPranchas(){
        List<Prancha> pranchas = _pranchasInterface.carregarTodasPranchas();
        for(Prancha prancha : pranchas){
            System.out.println("número da prancha:" + prancha.numeroPrancha); //TODO: UI - Texto
            System.out.println("nome prancha: " + prancha.nomePrancha); //TODO: UI - Texto
            System.out.println("data de criação prancha: " + prancha.dataHoraCriacao); //TODO: UI - Texto
        }
        System.out.println("Qual prancha deseja selecionar? [Número]"); //TODO: UI - Botão
        int numeroPrancha = scan.nextInt();
        selecionarPrancha(pranchas.get(numeroPrancha));
    }

    void visualizarHistoricoPrancha(){
        _pranchaInterface = new PranchaRepository(_pranchaSelecionada);
        _pranchaInterface.visualizarHistoricoPrancha();
    }

    void selecionarPrancha(Prancha pranchaSelecionada){
        _pranchaInterface = new PranchaRepository(pranchaSelecionada);
        _pranchaInterface.buscarConteudoAtualPrancha();
        System.out.println(_pranchaSelecionada.nomeArquivo);
    }

    void criarPrancha(){
        String nomePrancha = digitarNomePrancha(); //TODO: UI - TEXT BOX
        Prancha prancha = new Prancha(nomePrancha);
        _pranchaInterface = new PranchaRepository(prancha);
        _pranchaInterface.criarPrancha(); //TODO: UI - BOTÃO
    }

    void atualizarPrancha(){
        String nomePrancha = digitarNomePrancha(); //TODO: UI - TEXT BOX
        if(nomePrancha==null) nomePrancha = _pranchaSelecionada.nomePrancha;
        Prancha novaPrancha = new Prancha(nomePrancha);
        _pranchaInterface = new PranchaRepository(novaPrancha);

        _pranchaInterface.atualizarPrancha(); //TODO: UI - BOTÃO
    }

    void excluirPrancha(){
        _pranchaInterface = new PranchaRepository(_pranchaSelecionada);
        _pranchaInterface.excluirPrancha(); //TODO: UI - BOTÃO
    }

    static String digitarNomePrancha(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Digite o nome da prancha: ");
        return scan.nextLine();
    }
}
