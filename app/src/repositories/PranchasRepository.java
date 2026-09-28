package repositories;

import data.Prancha;
import interfaces.PranchasInterface;
import utils.FileHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PranchasRepository implements PranchasInterface {

    List<Prancha> _pranchas;
    String _caminho = "pranchas";
    FileHelper _fileHelper = new FileHelper(_caminho);

    public PranchasRepository(){
        _pranchas = buscarPranchasLocais();
    }

    public List<Prancha> carregarTodasPranchas(){
        return _pranchas;
    }

    public Prancha consultarPranchaComBaseNoNumero(int numero){
        return _pranchas.get(numero);
    }

    //TODO: corrigir valor
    public int definirNumeroPranchaAutomaticamente(){
        if (_pranchas.isEmpty()) return 0;
        return _pranchas.size() + 1;
    }

    public int contarQuantidadeDePranchas(){
        return _pranchas.size();
    }

    List<Prancha> buscarPranchasLocais(){
        List<Prancha> pranchas = new ArrayList<>();
        File[] files = _fileHelper.consultarVariosArquivos();
        for (var file : files){
            _fileHelper = new FileHelper(_caminho, file.getName());
            List<String> listaString = _fileHelper.consultarConteudoAtualArquivo();
            Prancha prancha = new Prancha().converterStringParaPrancha(listaString);
            pranchas.add(prancha);
        }
        return pranchas;
    }
}
