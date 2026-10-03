package utils;

import java.io.File;
import java.util.EmptyStackException;

public class FilesHelper {

    String _caminho;

    public FilesHelper(String caminhoPasta){
        _caminho = "database\\\\" + caminhoPasta;
    }

    public File[] consultarVariosArquivos() {
        File[] listaArquivos = new File[0];
        try{
            File file = new File(_caminho);
            listaArquivos = file.listFiles();
            return listaArquivos;
        }catch (EmptyStackException ex){
            System.out.println("Diretório não possui arquivos");;
        }
        return listaArquivos;
    }

    //TODO: corrigir valor
    public int definirNumeroObjetoAutomaticamente(){
        int numero;
        File[] arquivos = consultarVariosArquivos();
        if(arquivos != null && arquivos.length > 0){
            numero = consultarVariosArquivos().length;
            return numero + 1;
        }else{
            return 0;
        }
    }

    //NOTE: essa função deixou de ser usada depois que a pasta passou a ser criada automaticamente
    // na criação de prancha
    public void pastaExiste(){
        File arquivo = new File(_caminho);
        if(arquivo.exists()){
            if(!arquivo.isDirectory()){
                System.out.println("O caminho especificado não é o de um diretório");
            }
        }else{
            criarPasta();
        }
    }

    public void criarPasta(){
        //TODO: se pasta não existir criar pasta
        File arquivo = new File(_caminho);

        try {
            arquivo.mkdirs();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
