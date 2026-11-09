package utils;
import core.database.IDB;

import java.io.*;
import java.nio.file.Files;
import java.util.*;

public class FileHelper implements IDB {

    String _nomeArquivo;
    String _caminho;

    public FileHelper(String caminho, String nomeArquivo){
        _caminho = caminho;
        _nomeArquivo = "database\\\\" + _caminho + "\\\\" + nomeArquivo;
    }

    public List<String> consultar(){
        List<String> conteudoAtual = new ArrayList<>();
        try {
            List<String> linhasArquivo = Files.readAllLines(new File(_nomeArquivo).toPath());
            linhasArquivo.removeLast();
            List<String> bloco = new ArrayList<>();
            for(String linha : linhasArquivo){
                if (!Objects.equals(linha, "")){
                    bloco.add(linha);
                }else{
                    bloco.clear();
                }
            }
            conteudoAtual = bloco;
            return conteudoAtual;
        } catch (IOException e) {
            System.out.println("Erro ao consultar conteúdo atual do arquivo " + e.getMessage());
        }
        return conteudoAtual;
    }

    public void criar() {
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

    public void excluir(){
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

    public void escrever(List<String> conteudoArquivo){
        try (PrintWriter writer = new PrintWriter(new FileWriter(_nomeArquivo, true))){
            for (String s : conteudoArquivo) {
                writer.println(s);
            }
            writer.println("");
        }catch (IOException e){
            System.out.print("Erro ao escrever o conteúdo do arquivo: " + e.getMessage());
        }
    }

    //TODO: O método consultar arquivos do file helper sempre está retornando linhas vazias
    public List<List<String>> consultarHistorico(){
        List<String> linhasArquivo = new ArrayList<>();
        List<List<String>> listaLinhas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(_nomeArquivo))){
            String linha;
            while ((linha = reader.readLine()) != null){
                if (Objects.equals(linha, "")) {
                    listaLinhas.add(linhasArquivo);
                    linhasArquivo.clear();
                } else {
                    linhasArquivo.add(linha);
                }
            }
            return listaLinhas;
        }catch (IOException e){
            System.out.print("Erro ao consultar arquivo" + e.getMessage());
        }
        return listaLinhas;
    }

    public boolean existe(){
        File file = new File(_nomeArquivo);
        boolean arquivoExiste = file.exists();
        return !arquivoExiste;
    }
}