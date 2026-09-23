import data.Prancha;
import repositories.CardPranchaRepository;
import repositories.PranchaRepository;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //TODO: criar objeto frase -> esse objeto será o responsável por receber as palavras
        String nomePrancha = _digitarNomePrancha();
        var _pranchaRepository = new PranchaRepository(new Prancha(nomePrancha));
        _pranchaRepository.criarPrancha();
        _pranchaRepository.visualizarPrancha();
        _pranchaRepository.excluirPrancha();
    }

    static String _digitarNomePrancha(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Digite o nome da prancha: ");
        return scan.next();
    }
}
