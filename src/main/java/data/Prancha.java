package data;

import utils.DateTimeHelper;

import java.util.ArrayList;
import java.util.List;

public class Prancha{
    public int numeroPrancha;
    public String nomeArquivo;
    public String nomePrancha;
    public String dataHoraCriacao;

    public Prancha(String nomePrancha){
        this.nomePrancha = nomePrancha;
        this.dataHoraCriacao = new DateTimeHelper().buscarDataHoraAtual();
    }

    public Prancha(int numeroPrancha, String nomePrancha, String nomeArquivo){
        this.numeroPrancha = numeroPrancha;
        this.nomeArquivo = nomeArquivo;
        this.nomePrancha = nomePrancha;
        this.dataHoraCriacao = new DateTimeHelper().buscarDataHoraAtual();
    }

    //TODO: adicionar propriedades restantes
    public Prancha(String nomeArquivo, int numeroPrancha){
        this.numeroPrancha = numeroPrancha;
        this.nomeArquivo = nomeArquivo;
    }

    public Prancha(){}

    public Prancha empty(){
        return new Prancha(
            nomePrancha = ""
        );
    }


    public List<String> converterPranchaParaString(Prancha prancha){
        //NOTE: conforme vir mais campos utilizar o add para adicioná-los
        List<String> listaPrancha = new ArrayList<>();
        listaPrancha.add("numero:" + prancha.numeroPrancha);
        listaPrancha.add("nome:" + prancha.nomePrancha);
        listaPrancha.add("data:" + prancha.dataHoraCriacao);
        return listaPrancha;
    }

    public Prancha converterStringParaPrancha(List<String> listaString){
        Prancha prancha = new Prancha().empty();
        for(String s : listaString){
            String[] linha = s.split(":", 2);
            String chave = linha[0];
            String valor = linha[linha.length-1];
            prancha = mapearCamposEntidadePrancha(prancha, chave, valor);
        }
        return prancha;
    }


    public Prancha mapearCamposEntidadePrancha(Prancha prancha, String chave, String valor){
        //NOTE: conforme vir mais campos utilizar o switch para recuperá-los
        switch (chave){
            case "nome": prancha.nomePrancha = valor; break;
            case "data": prancha.dataHoraCriacao = valor;break;
        }
        return prancha;
    }
}