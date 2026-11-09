package repositories;

import core.database.IDB;
import data.Frase;
import interfaces.IFrase;
import utils.FileHelper;
import utils.FilesHelper;

import java.io.File;

//NOTE: a frase atual fica em database/frases/Frase_atual (um bloco por gravação, o último é o atual)
public class FraseRepository implements IFrase {

    String _caminho = "frases";
    String _nomeArquivo = "Frase_atual";
    IDB db;

    public FraseRepository() {
        new FilesHelper(_caminho).criarPasta();
        db = new FileHelper(_caminho, _nomeArquivo);
    }

    public Frase visualizarFrase() {
        if (!arquivoTemConteudo()) return new Frase();
        return new Frase().converterStringParaFrase(db.consultar());
    }

    public void salvarFrase(Frase frase) {
        //NOTE: FileHelper.existe() retorna true quando o arquivo NÃO existe
        if (db.existe()) db.criar();
        db.escrever(new Frase().converterFraseParaString(frase));
    }

    public void excluirFrase() {
        if (!db.existe()) db.excluir();
    }

    //NOTE: FileHelper.consultar() falha com arquivo vazio, por isso a checagem
    boolean arquivoTemConteudo() {
        File arquivo = new File("database", _caminho + File.separator + _nomeArquivo);
        return arquivo.exists() && arquivo.length() > 0;
    }
}
