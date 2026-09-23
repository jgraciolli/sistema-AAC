package utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FileHelper{

    String _nomeArquivo;

    public FileHelper(String nomeArquivo){
        _nomeArquivo = nomeArquivo;
    }

    //TODO: implementar biblioteca file

    public void criarArquivo() {
        try {
            File arquivo = new File(_nomeArquivo);
            var resultado = arquivo.createNewFile();
            if(!resultado) {
                System.out.println("Arquivo: " + _nomeArquivo + " já existe");
            }else{
                System.out.println("Arquivo criado com sucesso");
            }
        } catch (IOException e) {
            System.out.print("Erro ao criar arquivo: " + e.getMessage());
        }
    }

    public void excluirArquivo(){
        //TODO: excluir arquivo
        File arquivo = new File(_nomeArquivo);
        try {
            var resultado = arquivo.delete();
            if(resultado){
               System.out.println("Arquivo excluído com sucesso");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <T> void escreverConteudoArquivo(List<String> conteudoArquivo){
        try (PrintWriter writer = new PrintWriter(new FileWriter(_nomeArquivo))){
            for (String s : conteudoArquivo) {
                //TODO: guardar chave e valor
                writer.print(s);
            }
            writer.println();
            writer.print("+");
        }catch (IOException e){
            System.out.print("Erro ao escrever o conteúdo do arquivo: " + e.getMessage());
        }
    }

    public List<String> consultarConteudoArquivo(){
        List<String> linhasArquivo = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(_nomeArquivo))){
            String linha;
            while ((linha = reader.readLine()) != null){
                linhasArquivo.add(linha);
            }
            return linhasArquivo;
        }catch (IOException e){
            System.out.print("Erro ao consultar arquivo" + e.getMessage());
        }
        return linhasArquivo;
    }

    public void consultarConteudoVariosArquivos(){
        //TODO: verificar se a pasta não é vazia
        //TODO: identificar todos os ARQUIVOS da pasta disponíveis
        //TODO: (foreach) para cada ARQUIVO guardar conteúdo em um novo objeto
        //TODO: adicionar objeto a nova lista
        //TODO: retornar lista de objetos
    }
}