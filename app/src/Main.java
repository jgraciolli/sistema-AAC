import java.util.scanner;


public class Main {
    public static void main(String[] args) {
        //TODO: criar objeto frase -> esse objeto será o responsável por receber as palavras

    }

    criarPrancha(){
        //TODO: criar objeto da prancha
        //TODO: armazenar em novo ARQUIVO prancha
    }

    editarPrancha(){
        //TODO: criar novo objeto de prancha
        //TODO: (parametro) receber nome da prancha
        //TODO: localizar ultimo objeto da prancha no ARQUIVO
        //TODO: editar campo "excluido?" no ultimo objeto no ARQUIVO prancha
        //TODO: escrever novo objeto abaixo da ultima linha do ARQUIVO
    }

    excluirPrancha(){
        //TODO: (parametro) receber nome da prancha
        //TODO: excluir ARQUIVO prancha
    }

    criarCard(){
        //TODO: criar objeto de card
        //TODO: armazenar em novo ARQUIVO card
    }

    editarCard(){
        //TODO: criar novo objeto de card
        //TODO: (parametro) receber nome do card
        //TODO: localizar ultimo objeto de card no ARQUIVO
        //TODO: editar campo excluído no ultimo objeto no ARQUIVO card
        //TODO: escrever novo objeto abaixo da ultima linha do ARQUIVO
    }

    excluirCard(){
        //TODO: (parametro) receber nome do card
        //TODO: excluir ARQUIVO card
    }

    reproduzirPalavra(){
        //DOCS: pesquisar biblioteca capaz de reproduzir uma palavra
    }

    reordenarCardsNaPrancha(){
        //TODO: (parametro) receber card que deseja mudar
        //TODO: (parametro) receber novo indice
        //TODO: consultar todos os ARQUIVOS dessa prancha
        //RN: (foreach) para cada o indice de cada card da lista -> indice = indice - 1
        //RN: para o card com indice 0 -> indice = tamanho da lista + 1
        //TODO: (foreach) para cada card da lista gravar em seu respectivo arquivo (utilizar nome do objeto como chave)
    }

    gerarImagensComBaseNasPalavras(){
        //DOCS: pesquisar IA capaz de gerar imagens com base em palavras
    }

    atribuirImagemAoCard(){
        //TODO: (parametro) receber nome do card
        //TODO: atualizar nome da imagem com base no card
        //OBS: (nome_imagem)_(data_atual)_(nome_card).(tipo_imagem)
    }

    //OBS: Construir frase -> Adicionar palavra do card a frase

    adicionarPalavraDoCardAFrase(){
        //UI: esse método deve ser chamado ao clicar em qualquer lugar no card de palavra
        //TODO: receber palavra do card selecionado
        //TODO: retornar palavra
    }

    reproduzirFrase(){
        //DOCS: pesquisar IA capaz de gerar imagens com base em palavras
    }
}

public class CardPalavra{
    String categoria;
    String palavra;
    String imagem; //TODO: desenvolver objeto de imagem
    String cor; //TODO: desenvolver enumerador de cores
    int ordem;
    Prancha prancha;
}

public class Prancha{
    nome;
}