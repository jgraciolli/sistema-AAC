package repositories;

import data.Prancha;
import interfaces.PranchaInterface;
import utils.FileHelper;

import java.util.*;

public class PranchaRepository implements PranchaInterface {

    FileHelper _fileHelper;
    Prancha _pranchaSelecionada;
    String caminho = "pranchas";

    public PranchaRepository(Prancha prancha){
        int numeroPrancha = new PranchasRepository().definirNumeroPranchaAutomaticamente();
        _pranchaSelecionada = new Prancha(numeroPrancha, prancha.nomePrancha);
        _fileHelper = new FileHelper(caminho, _pranchaSelecionada.nomeArquivo);
    };

    public void criarPrancha(){
        if (!_fileHelper.arquivoExiste()) {
            _fileHelper.criarArquivo();
            //RN: apenas a primeira escrita
            List<String> listaPrancha = new Prancha().converterPranchaParaString(_pranchaSelecionada);
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
        List<String> listaPrancha = new Prancha().converterPranchaParaString(_pranchaSelecionada);
        _fileHelper.escreverConteudoArquivo(listaPrancha);
    }

    public void excluirPrancha(){
        _fileHelper.excluirArquivo();
    }

    public Prancha buscarConteudoAtualPrancha(){
        List<String> listaString = _fileHelper.consultarConteudoAtualArquivo();
        return new Prancha().converterStringParaPrancha(listaString);
    }

    //TODO: trazer além do nome o conteúdo inteiro do card nas propriedades do arquivo
    String consultarNomePrancha(){
        return _fileHelper.consultarPropriedadesDoArquivo().getName();
    }
}
