package core.database;

import java.util.List;

public interface IDB {
    List<String> consultar();
    void criar();
    void excluir();
    void escrever(List<String> conteudoArquivo);
    public List<List<String>> consultarHistorico();
    public boolean existe();
}
