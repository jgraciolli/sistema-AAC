package services;

import com.fasterxml.jackson.databind.JsonNode;
import data.CardPalavra;
import data.Prancha;
import interfaces.IGeradorImagem;
import utils.ArasaacHelper;
import utils.ArasaacHelper.Pictograma;
import utils.OllamaHelper;

import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PictogramaServices implements IGeradorImagem {

    private static final int MAXIMO_OPCOES = 10;
    private static final int MAXIMO_TERMOS_ALTERNATIVOS = 3;

    private final ArasaacHelper _arasaac = new ArasaacHelper();
    private final OllamaHelper _ollama = OllamaHelper.instancia();

    @Override
    public byte[] gerarImagem(CardPalavra cardPalavra, Prancha prancha) throws IOException {
        String palavra = cardPalavra.palavraCard;
        List<Pictograma> opcoes = _arasaac.buscar(palavra);
        if (opcoes.isEmpty()) {
            opcoes = buscarPorTermosAlternativos(cardPalavra, prancha);
        }
        if (opcoes.isEmpty()) {
            throw new IOException("nenhum pictograma foi encontrado para \"" + palavra + "\".");
        }
        Pictograma escolhido = escolherPictograma(opcoes, cardPalavra, prancha);
        System.out.println("Pictograma " + escolhido.id() + " " + escolhido.palavras() + " escolhido para \"" + palavra + "\"");
        return _arasaac.baixarImagem(escolhido.id());
    }

    private Pictograma escolherPictograma(List<Pictograma> opcoes, CardPalavra cardPalavra, Prancha prancha) {
        String palavra = normalizar(cardPalavra.palavraCard);
        List<Pictograma> exatos = opcoes.stream()
                .filter(p -> p.palavras().stream().anyMatch(k -> normalizar(k).equals(palavra)))
                .toList();
        List<Pictograma> filtrados = exatos.isEmpty() ? opcoes : exatos;
        List<Pictograma> candidatos = filtrados.subList(0, Math.min(MAXIMO_OPCOES, filtrados.size()));
        if (candidatos.size() == 1) return candidatos.getFirst();

        StringBuilder lista = new StringBuilder();
        List<Integer> ids = new ArrayList<>();
        for (Pictograma p : candidatos) {
            ids.add(p.id());
            lista.append("- ").append(p.id()).append(": ")
                    .append(String.join(", ", p.palavras()))
                    .append(" | significado: ").append(resumir(p.significado(), 140))
                    .append(" | temas: ").append(String.join(", ", p.categorias().subList(0, Math.min(6, p.categorias().size()))))
                    .append('\n');
        }

        String prompt = """
                Você ajuda a montar pranchas de Comunicação Aumentativa e Alternativa (CAA).
                Cada card de uma prancha tem uma palavra e uma categoria, e precisa de um pictograma.

                Prancha: "%s"
                Palavra do card: "%s"
                Categoria do card: "%s"

                Pictogramas encontrados (id: palavras | significado | temas):
                %s
                Uma mesma palavra pode ter sentidos diferentes. Pense primeiro no assunto da prancha:
                o pictograma escolhido precisa fazer sentido para quem está usando uma prancha sobre "%s".
                Use a categoria do card só para desempatar.
                Responda apenas em JSON com o campo "id" do pictograma escolhido.
                """.formatted(prancha.nomePrancha, cardPalavra.palavraCard, cardPalavra.categoriaCard, lista, prancha.nomePrancha);

        Map<String, Object> formato = Map.of(
                "type", "object",
                "properties", Map.of("id", Map.of("type", "integer", "enum", ids)),
                "required", List.of("id"));

        try {
            int id = _ollama.perguntar(prompt, formato).path("id").asInt(-1);
            for (Pictograma p : candidatos) {
                if (p.id() == id) return p;
            }
            System.out.println("O Ollama respondeu um id fora da lista (" + id + "). Usando o primeiro resultado do ARASAAC.");
        } catch (IOException e) {
            System.out.println("Não foi possível usar a IA para escolher o pictograma (" + e.getMessage() + "). Usando o primeiro resultado do ARASAAC.");
        }
        return candidatos.getFirst();
    }

    private List<Pictograma> buscarPorTermosAlternativos(CardPalavra cardPalavra, Prancha prancha) throws IOException {
        String prompt = """
                Você ajuda a montar pranchas de Comunicação Aumentativa e Alternativa (CAA).
                A busca de pictogramas pela palavra "%s" não encontrou resultados.
                Prancha: "%s"
                Categoria do card: "%s"

                Sugira até %d palavras em português do Brasil, simples, no singular e escritas corretamente,
                que tenham o mesmo sentido ou um sentido bem próximo, para buscar o pictograma de novo.
                Responda apenas em JSON com o campo "termos", uma lista de palavras.
                """.formatted(cardPalavra.palavraCard, prancha.nomePrancha, cardPalavra.categoriaCard, MAXIMO_TERMOS_ALTERNATIVOS);

        Map<String, Object> formato = Map.of(
                "type", "object",
                "properties", Map.of("termos", Map.of("type", "array", "items", Map.of("type", "string"))),
                "required", List.of("termos"));

        JsonNode resposta;
        try {
            resposta = _ollama.perguntar(prompt, formato);
        } catch (IOException e) {
            System.out.println("Não foi possível usar a IA para sugerir outros termos (" + e.getMessage() + ").");
            return List.of();
        }

        int tentativas = 0;
        for (JsonNode termo : resposta.path("termos")) {
            if (tentativas++ >= MAXIMO_TERMOS_ALTERNATIVOS) break;
            String texto = termo.asText().trim();
            if (texto.isBlank() || texto.equalsIgnoreCase(cardPalavra.palavraCard)) continue;
            List<Pictograma> encontrados = _arasaac.buscar(texto);
            if (!encontrados.isEmpty()) {
                System.out.println("Pictogramas encontrados pelo termo alternativo \"" + texto + "\"");
                return encontrados;
            }
        }
        return List.of();
    }

    private String normalizar(String texto) {
        String semAcento = Normalizer.normalize(texto, Normalizer.Form.NFD).replaceAll("\\p{M}", "");
        return semAcento.toLowerCase().replaceAll("[^a-z0-9]+", " ").trim();
    }

    private String resumir(String texto, int limite) {
        String limpo = texto.replaceAll("\\s+", " ").trim();
        return limpo.length() <= limite ? limpo : limpo.substring(0, limite) + "...";
    }
}
