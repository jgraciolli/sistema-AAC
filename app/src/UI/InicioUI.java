package UI;

import java.util.Scanner;

public class InicioUI {

    Scanner scan;
    PranchaUI telaPrancha;

    public InicioUI(){
        scan = new Scanner(System.in);
        telaPrancha = new PranchaUI();
    }

    public void renderizarTelaInicial() {
        System.out.println("SEJA BEM VINDO AO SISTEMA AAC CALOPSITO"); //TODO: UI - TEXTO
        System.out.println("O QUE DESEJA FAZER?"); //TODO: UI - TEXTO
        System.out.println("1 - Consultar pranchas"); //TODO: UI - BOTÃO
        System.out.println("2 - Criar nova prancha"); //TODO: UI - BOTÃO
        System.out.println("3 - Atualizar Prancha"); //TODO: UI - BOTÃO
        System.out.println("4 - Excluir Prancha"); //TODO: UI - BOTÃO
        System.out.println("5 - Visualizar histórico da Prancha"); //TODO: UI - BOTÂO
        selecionarOpcao();
    }

    //NOTE: método temporário
    void selecionarOpcao(){
        int opcao = scan.nextInt();
        switch (opcao){
            case 1: telaPrancha.carregarTodasPranchas();
            case 2: telaPrancha.criarPrancha();
            case 3: telaPrancha.atualizarPrancha();
            case 4: telaPrancha.excluirPrancha();
            case 5: telaPrancha.visualizarHistoricoPrancha();
        }
    }
}
