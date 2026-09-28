import data.Prancha;
import repositories.CardPranchaRepository;
import repositories.PranchaRepository;
import repositories.PranchasRepository;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //TODO: criar objeto frase -> esse objeto será o responsável por receber as palavras
//        String nome = _digitarNomePrancha();
        var _pranchaRepository = new PranchaRepository(new Prancha("prancha teste"));
        _pranchaRepository.criarPrancha();
//        _pranchaRepository.atualizarPrancha();
//        var pranchas = _pranchaRepository.visualizarHistoricoPrancha();
//        var novaPrancha = new Prancha("Prancha_2");
//        _pranchaRepository.editarPrancha(novaPrancha);
//        _pranchaRepository.excluirPrancha();
//        var prancha = _pranchaRepository.buscarConteudoAtualPrancha();
    }

    static String _digitarNomePrancha(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Digite o nome da prancha: ");
        return scan.nextLine();
    }
}
