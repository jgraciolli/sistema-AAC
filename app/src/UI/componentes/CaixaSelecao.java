package UI.componentes;

import java.util.Objects;
import java.util.Scanner;

public class CaixaSelecao {

    static Scanner _scan = new Scanner(System.in);
    static String _mensagem;

    public CaixaSelecao(String mensagem){
        _mensagem = mensagem;
    }

    public CaixaSelecao(){
    }

    public static int selecionar(){
        System.out.println(Objects.requireNonNullElse(_mensagem, "O QUE DESEJA FAZER?"));
        return _scan.nextInt();
    }

}
