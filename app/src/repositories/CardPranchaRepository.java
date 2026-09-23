package repositories;

public class CardPranchaRepository {

    public void criarCard(){
        //TODO: criar objeto de card
        //TODO: armazenar em novo ARQUIVO card
    }

    public void editarCard(){
        //TODO: criar novo objeto de card
        //TODO: (parametro) receber nome do card
        //TODO: localizar ultimo objeto de card no ARQUIVO
        //TODO: editar campo excluído no ultimo objeto no ARQUIVO card
        //TODO: escrever novo objeto abaixo da ultima linha do ARQUIVO
    }

    public void excluirCard(){
        //TODO: (parametro) receber nome do card
        //TODO: excluir ARQUIVO card
    }

    public void reproduzirPalavra(){
        //DOCS: pesquisar biblioteca capaz de reproduzir uma palavra
    }

    //OBS: Construir frase -> Adicionar palavra do card a frase

    public void adicionarPalavraDoCardAFrase(){
        //UI: esse método deve ser chamado ao clicar em qualquer lugar no card de palavra
        //TODO: receber palavra do card selecionado
        //TODO: retornar palavra
    }

    public void reproduzirFrase(){
        //DOCS: pesquisar IA capaz de gerar imagens com base em palavras
    }

    //TODO: construir um repositório apenas para imagens

    public void gerarImagensComBaseNasPalavras(){
        //DOCS: pesquisar IA capaz de gerar imagens com base em palavras
    }

    public void atribuirImagemAoCard(){
        //TODO: (parametro) receber nome do card
        //TODO: atualizar nome da imagem com base no card
        //OBS: (nome_imagem)_(data_atual)_(nome_card).(tipo_imagem)
    }
}
