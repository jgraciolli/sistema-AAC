package repositories;

import data.Prancha;
import interfaces.IPrancha;
import utils.FileHelper;
import utils.FilesHelper;

import java.util.*;

public class PranchaRepository implements IPrancha {

    FileHelper _fileHelper;
    FilesHelper _filesHelper;
    String _caminho = "pranchas";
    Prancha _prancha;

    public PranchaRepository(Prancha prancha){
        String nomePrancha = "Prancha_" + prancha.numeroPrancha;
        _prancha = new Prancha(prancha.numeroPrancha, prancha.nomePrancha, nomePrancha);
        _fileHelper = new FileHelper(_caminho, nomePrancha);
        _filesHelper = new FilesHelper("cardsPalavras\\\\" + nomePrancha);
    }

    public void criarPrancha(){
        _filesHelper = new FilesHelper(_caminho);
        int numero = _filesHelper.definirNumeroObjetoAutomaticamente();
        _prancha = new Prancha(numero, _prancha.nomePrancha, "Prancha_" + numero);
        _fileHelper = new FileHelper(_caminho, _prancha.nomeArquivo);
        if (_fileHelper.existe()) {
            _fileHelper.criar();
            _filesHelper = new FilesHelper("cardsPalavras\\\\"  + _prancha.nomeArquivo);
            _filesHelper.criarPasta();
            //RN: apenas a primeira escrita
            List<String> listaPrancha = new Prancha().converterPranchaParaString(_prancha);
            _fileHelper.escrever(listaPrancha);
        }else{
            System.out.print("Prancha já existe");
        }
    }

    public List<Prancha> visualizarHistoricoPrancha(){
        List<List<String>> listaLinhas = _fileHelper.consultarHistorico();
        List<Prancha> listaPranchas = new ArrayList<>();
        for(var i : listaLinhas){
            Prancha prancaFormatada = new Prancha().converterStringParaPrancha(i);
            listaPranchas.add(prancaFormatada);
        }
        return listaPranchas;
    }

    public void atualizarPrancha(){
        List<String> listaPrancha = new Prancha().converterPranchaParaString(_prancha);
        _fileHelper.escrever(listaPrancha);
    }

    public void excluirPrancha(){
        _fileHelper.excluir();
        _filesHelper.excluirPasta();
    }
}
