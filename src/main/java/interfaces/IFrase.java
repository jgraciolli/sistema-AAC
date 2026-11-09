package interfaces;

import data.Frase;

public interface IFrase {
    Frase visualizarFrase();
    void salvarFrase(Frase frase);
    void excluirFrase();
}
