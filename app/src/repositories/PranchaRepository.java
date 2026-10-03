package repositories;

import data.Prancha;
import interfaces.PranchaInterface;
import utils.FileHelper;
import utils.FilesHelper;

import java.util.*;

public class PranchaRepository implements PranchaInterface {

    FileHelper _fileHelper;
    FilesHelper _filesHelper;
    String _caminho = "pranchas";
    String _pastaPrancha;
    Prancha _prancha;

    public PranchaRepository(Prancha prancha){
        _filesHelper = new FilesHelper(_caminho);
        _prancha = new Prancha(_filesHelper.definirNumeroObjetoAutomaticamente(), prancha.nomePrancha);
        _fileHelper = new FileHelper(_caminho, _prancha.nomeArquivo);

    }

    public void criarPrancha(){
        if (!_fileHelper.arquivoExiste()) {
            _fileHelper.criarArquivo();
            _filesHelper = new FilesHelper("cardsPalavras\\\\"  + _prancha.nomeArquivo);
            _filesHelper.criarPasta();
            //RN: apenas a primeira escrita
            List<String> listaPrancha = new Prancha().converterPranchaParaString(_prancha);
            _fileHelper.escreverConteudoArquivo(listaPrancha);
        }else{
            System.out.print("Prancha já existe");
        }
    }

    public List<Prancha> visualizarHistoricoPrancha(){
        List<List<String>> listaLinhas = _fileHelper.consultarConteudoArquivo();
        List<Prancha> listaPranchas = new ArrayList<>();
        for(var i : listaLinhas){
            Prancha prancaFormatada = new Prancha().converterStringParaPrancha(i);
            listaPranchas.add(prancaFormatada);
        }
        return listaPranchas;
    }

    public void atualizarPrancha(){
        List<String> listaPrancha = new Prancha().converterPranchaParaString(_prancha);
        _fileHelper.escreverConteudoArquivo(listaPrancha);
    }

    public void excluirPrancha(){
        _fileHelper.excluirArquivo();
    }

    //TODO: trazer além do nome o conteúdo inteiro do card nas propriedades do arquivo
    String consultarNomePrancha(){
        return _fileHelper.consultarPropriedadesDoArquivo().getName();
    }
}
