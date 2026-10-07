package services;

import data.Prancha;
import repositories.CardsPalavraRepository;

public class FraseServices {
    //OBS: Construir frase -> Adicionar palavra do card a frase

    CardsPalavraRepository _cardsPalavras;

    public FraseServices(){
            _cardsPalavras = new CardsPalavraRepository();
    }

    public void adicionarPalavraDoCardAFrase(){
        //UI: esse método deve ser chamado ao clicar em qualquer lugar no card de palavra
        //TODO: receber palavra do card selecionado
        //TODO: retornar palavra
    }

    public void reproduzirFrase(){
        //DOCS: pesquisar IA capaz de gerar imagens com base em palavras
    }

    //TODO: construir um repositório apenas para imagens
}
