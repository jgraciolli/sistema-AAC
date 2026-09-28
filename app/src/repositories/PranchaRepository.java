package repositories;

import data.Prancha;
import utils.FileHelper;

import java.util.*;

public class PranchaRepository {

    FileHelper _fileHelper;
    Prancha _prancha;
    String caminho = "pranchas";
    PranchasRepository pranchasRepository;

    public PranchaRepository(Prancha prancha){
        pranchasRepository = new PranchasRepository();
        _prancha = new Prancha(pranchasRepository.definirNumeroPranchaAutomaticamente(), prancha.nomePrancha); //TODO: criar nome arquivo da prancha com base em um contador
        _fileHelper = new FileHelper(caminho, _prancha.nomeArquivo);
    };

    public void criarPrancha(){
        if (!_fileHelper.arquivoExiste()) {
            _fileHelper.criarArquivo();
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

    public Prancha buscarConteudoAtualPrancha(){
        List<String> listaString = _fileHelper.consultarConteudoAtualArquivo();
        return new Prancha().converterStringParaPrancha(listaString);
    }

    //TODO: trazer além do nome o conteúdo inteiro do card nas propriedades do arquivo
    String consultarNomePrancha(){
        return _fileHelper.consultarPropriedadesDoArquivo().getName();
    }

}
