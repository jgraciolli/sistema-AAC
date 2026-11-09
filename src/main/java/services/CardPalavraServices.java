package services;

public class CardPalavraServices {
    public void reproduzirPalavra(){
        //DOCS: pesquisar biblioteca capaz de reproduzir uma palavra
    }
    public void gerarImagensComBaseNasPalavras(){
        //DOCS: pesquisar IA capaz de gerar imagens com base em palavras
    }

    public void atribuirImagemAoCard(){
        //TODO: (parametro) receber nome do card
        //TODO: atualizar nome da imagem com base no card
        //OBS: (nome_imagem)_(data_atual)_(nome_card).(tipo_imagem)
    }

    public void reordenarCardsNaPrancha(){
        //TODO: (parametro) receber card que deseja mudar
        //TODO: (parametro) receber novo indice
        //TODO: consultar todos os ARQUIVOS dessa prancha
        //RN: (foreach) para cada o indice de cada card da lista -> indice = indice - 1
        //RN: para o card com indice 0 -> indice = tamanho da lista + 1
        //TODO: (foreach) para cada card da lista gravar em seu respectivo arquivo (utilizar nome do objeto como chave)
    }
}
