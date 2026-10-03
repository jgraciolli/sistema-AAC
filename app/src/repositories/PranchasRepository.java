package repositories;

import data.Prancha;
import interfaces.PranchasInterface;
import utils.FileHelper;
import utils.FilesHelper;

import java.util.ArrayList;
import java.util.List;

public class PranchasRepository implements PranchasInterface {

    List<Prancha> _pranchas;
    String _caminho = "pranchas";
    FilesHelper _filesHelper;
    FileHelper _fileHelper;

    public PranchasRepository(){
        _filesHelper = new FilesHelper(_caminho);
        _pranchas = buscarPranchasLocais();
    }

    public List<Prancha> carregarTodasPranchas(){
        return _pranchas;
    }

    public Prancha consultarPranchaComBaseNoNumero(int numero){
        return _pranchas.get(numero);
    }

    public int contarQuantidadeDePranchas(){
        return _pranchas.size();
    }

    List<Prancha> buscarPranchasLocais(){
        List<Prancha> pranchas = new ArrayList<>();
        var files = _filesHelper.consultarVariosArquivos();
        for (var file : files){
            _fileHelper = new FileHelper(_caminho, file.getName());
            List<String> listaString = _fileHelper.consultarConteudoAtualArquivo();
            Prancha prancha = new Prancha().converterStringParaPrancha(listaString);;
            prancha.nomeArquivo = file.getName();
            prancha.numeroPrancha = consultarNumeroPrancha(file.getName());
            pranchas.add(prancha);
        }
        return pranchas;
    }

    //TODO: verificar para transformar em método genérico futuramente
    int consultarNumeroPrancha(String nomeArquivo){
        return Integer.parseInt(nomeArquivo.replace("Prancha_", ""));
    }
}
