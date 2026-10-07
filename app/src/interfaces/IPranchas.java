package interfaces;

import data.Prancha;

import java.util.List;

public interface IPranchas {
    List<Prancha> carregarTodasPranchas();
    Prancha consultarPranchaComBaseNoNumero(int numero);
}
