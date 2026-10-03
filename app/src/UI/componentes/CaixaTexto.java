package UI.componentes;

import java.util.Scanner;

public class CaixaTexto {

    //TODO: UI - CAIXA DE TEXTO

    static Scanner _scan;
    static String _mensagem;

    public CaixaTexto(String mensagem){
        _scan = new Scanner(System.in);
        _mensagem = mensagem;
    }

    public static String digitar(){
        System.out.println(_mensagem);
        return _scan.nextLine();
    }
}
