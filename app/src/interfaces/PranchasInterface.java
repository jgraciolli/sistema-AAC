package interfaces;

import data.Prancha;

import java.util.List;

public interface PranchasInterface {
    List<Prancha> carregarTodasPranchas();
    Prancha consultarPranchaComBaseNoNumero(int numero);
}
