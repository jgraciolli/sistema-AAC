package repositories;

import data.Prancha;
import utils.FileHelper;

import java.lang.reflect.Field;
import java.util.*;

public class PranchaRepository {

    FileHelper _fileHelper;
    Prancha _prancha;

    public PranchaRepository(Prancha prancha){
        _prancha = new Prancha(prancha.nome);
        //RN: nome da prancha vem da tela
        _fileHelper = new FileHelper(_prancha.nome);

    };

    public void criarPrancha(){
        _fileHelper.criarArquivo();
        //NOTE: conforme vir mais campos utilizar o add para adicioná-los
        List<String> listaPrancha = converterPranchaParaString();
        _fileHelper.escreverConteudoArquivo(listaPrancha);
    }

    public Prancha visualizarPrancha(){
        List<String> linhasArquivo = _fileHelper.consultarConteudoArquivo();
        return converterStringParaPrancha(linhasArquivo);
    }

    public void editarPrancha(){
        //TODO: criar novo objeto de prancha
        //TODO: (parametro) receber nome da prancha
        //TODO: localizar ultimo objeto da prancha no ARQUIVO
        //TODO: editar campo "excluido?" no ultimo objeto no ARQUIVO prancha
        //TODO: escrever novo objeto abaixo da ultima linha do ARQUIVO
    }

    public void excluirPrancha(){
        _fileHelper.excluirArquivo();
    }


    public void reordenarCardsNaPrancha(){
        //TODO: (parametro) receber card que deseja mudar
        //TODO: (parametro) receber novo indice
        //TODO: consultar todos os ARQUIVOS dessa prancha
        //RN: (foreach) para cada o indice de cada card da lista -> indice = indice - 1
        //RN: para o card com indice 0 -> indice = tamanho da lista + 1
        //TODO: (foreach) para cada card da lista gravar em seu respectivo arquivo (utilizar nome do objeto como chave)
    }

    List<String> converterPranchaParaString(){
        List<String> listaPrancha = new ArrayList<>();
        listaPrancha.add("nome:" + _prancha.nome);
        return listaPrancha;
    }

    Prancha converterStringParaPrancha(List<String> listaString){
        //TODO: pegar a lista de string e converter em objeto prancha
        Prancha prancha = new Prancha().empty();
        for(String s : listaString){
            //TODO: se s for + seguir para a próxima linha
            String[] linha = s.split(":");
            String chave = linha[0];
            String valor = linha[linha.length-1];
            prancha = mapearCamposEntidadePrancha(prancha, chave, valor);
        }
        return prancha;
    }

    Prancha mapearCamposEntidadePrancha(Prancha prancha, String chave, String valor){
        switch (chave){
            case "nome": prancha.nome = valor;
            //NOTE: adicionar campos conforme entidade cresce
        }
        return prancha;
    }


}
