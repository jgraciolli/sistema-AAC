package utils;

import java.io.File;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;

//NOTE: esse helper é descartável quando inserido o banco de dados
// passaremos provavelmente a utilizar QUERY ao invés de ‘loops’

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
        File[] files = consultarVariosArquivos();
        List<Integer> numeros = new ArrayList<>();
        String nome;
        for(File i : files){
            nome = i.getName();
            String[] aux = nome.split("_", 2);
            numeros.add(Integer.parseInt(aux[1]));
        }
        return processarNumeroFaltante(numeros);

    }

    private int processarNumeroFaltante(List<Integer> numeros){

        /*
        * 0
        * 1
        * 2
        * 3
        * RN: se o próximo número for diferente de n+1 retorne n+1*/

        int numAux = 0;

        for(int numero : numeros){
            if(numAux!=0){
                if(numAux + 1 != numero){
                    return numAux + 1;
                }
            }
            ///RN: na primeira vez não vai acontecer nada porque só tem um valor
            numAux = numero;
            if(numeros.getLast() == numero){
                return numero + 1;
            }
        }
        return 1;
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
    public void excluirPasta(){
        File arquivo = new File(_caminho);
        try {
            arquivo.delete();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
