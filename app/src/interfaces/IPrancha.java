package interfaces;

import data.Prancha;

import java.util.List;

public interface IPrancha {
    List<Prancha> visualizarHistoricoPrancha();
    void criarPrancha();
    void atualizarPrancha();
    void excluirPrancha();
}
