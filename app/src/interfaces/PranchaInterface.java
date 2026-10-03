package interfaces;

import data.Prancha;

import java.util.List;

public interface PranchaInterface {
    List<Prancha> visualizarHistoricoPrancha();
    void criarPrancha();
    void atualizarPrancha();
    void excluirPrancha();
}
