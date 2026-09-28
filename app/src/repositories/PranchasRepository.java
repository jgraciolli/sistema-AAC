package repositories;

import data.Prancha;
import utils.FileHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PranchasRepository {

    List<Prancha> _pranchas;
    PranchaRepository pranchaRepository;
    FileHelper _fileHelper;
    String _caminho = "pranchas";

    PranchasRepository(){//TODO: listar todos os arquivos existentes em determinado caminho
        _fileHelper = new FileHelper(_caminho);
        _pranchas = new ArrayList<>();
        converterArquivosParaPranchas();
    }

    List<Prancha> consultarVariasPranchas(){
        return _pranchas;
    }

    Prancha consultarPranchaComBaseNoNumero(int numero){
        return _pranchas.get(numero);
    }

    int definirNumeroPranchaAutomaticamente(){
        return _pranchas.getLast().numeroPrancha + 1;
    }

    int contarQuantidadeDePranchas(){
        return _pranchas.size();
    }

    void converterArquivosParaPranchas(){
        File[] files = _fileHelper.consultarVariosArquivos();
        for (var file : files){
            var prancha = new Prancha(file.getName(), Integer.parseInt(file.getName().replaceAll("\\D", "")));
            _pranchas.add(prancha);
        }
    }
}
