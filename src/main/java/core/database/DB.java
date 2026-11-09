package core.database;

import java.util.List;

public class DB implements IDB{

    @Override
    public List<String> consultar() {
        return List.of();
    }

    @Override
    public void criar() {

    }

    @Override
    public void excluir() {

    }

    @Override
    public void escrever(List<String> conteudoArquivo) {

    }

    @Override
    public List<List<String>> consultarHistorico() {
        return List.of();
    }

    @Override
    public boolean existe() {
        return false;
    }
}
