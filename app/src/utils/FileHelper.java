package utils;

import java.io.File;
import java.io.IOException;

public class FileHelper{

    File _arquivo;

    public FileHelper(File arquivo){
        _arquivo = arquivo;
    }

    //TODO: implementar biblioteca file

    public void criarArquivo() {
        try {
            var arquivoExiste = _arquivo.createNewFile();
            if(arquivoExiste) {
                System.out.println("Arquivo: " + _arquivo.getName() + " já existe");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void excluirArquivo(){
        //TODO: excluir arquivo
        _arquivo.delete();
    }

    public void escreverConteudoArquivo(){
        //TODO: verificar se arquivo existe
        //TODO: buscar por nome do arquivo
        //TODO: abrir arquivo
        //TODO: verificar se o conteúdo está vazio
        //TODO: (insert) adicionar texto abaixo da última linha do arquivo
    }

    public void consultarConteudoArquivo(){
        //TODO: verificar se o arquivo existe
        //TODO: buscar por nome do arquivo
        //TODO: abrir arquivo
        //TODO: guardar em memória conteúdo do arquivo
        //TODO: (return) retornar arquivo
    }

    public void consultarConteudoVariosArquivos(){
        //TODO: verificar se a pasta não é vazia
        //TODO: identificar todos os ARQUIVOS da pasta disponíveis
        //TODO: (foreach) para cada ARQUIVO guardar conteúdo em um novo objeto
        //TODO: adicionar objeto a nova lista
        //TODO: retornar lista de objetos
    }
}