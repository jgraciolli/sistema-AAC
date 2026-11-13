package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class ArasaacHelper {

    private static final String URL_BUSCA = "https://api.arasaac.org/v1/pictograms/pt/search/";
    private static final String URL_IMAGEM = "https://static.arasaac.org/pictograms/%d/%d_500.png";

    private static final HttpClient CLIENTE = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .followRedirects(HttpClient.Redirect.NORMAL)
            .build();
    private static final ObjectMapper JSON = new ObjectMapper();

    public record Pictograma(int id, List<String> palavras, String significado, List<String> categorias) {}

    public List<Pictograma> buscar(String termo) throws IOException {
        String termoUrl = URLEncoder.encode(termo.trim().toLowerCase(), StandardCharsets.UTF_8).replace("+", "%20");
        HttpResponse<String> resposta = enviar(URL_BUSCA + termoUrl, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        if (resposta.statusCode() == 404) return List.of();
        if (resposta.statusCode() != 200) {
            throw new IOException("o ARASAAC respondeu com o código " + resposta.statusCode() + ".");
        }

        List<Pictograma> pictogramas = new ArrayList<>();
        for (JsonNode item : JSON.readTree(resposta.body())) {
            List<String> palavras = new ArrayList<>();
            String significado = "";
            for (JsonNode chave : item.path("keywords")) {
                palavras.add(chave.path("keyword").asText());
                if (significado.isBlank()) significado = chave.path("meaning").asText("");
            }
            List<String> categorias = new ArrayList<>();
            item.path("categories").forEach(c -> adicionarSemRepetir(categorias, c.asText()));
            item.path("tags").forEach(t -> adicionarSemRepetir(categorias, t.asText()));
            pictogramas.add(new Pictograma(item.path("_id").asInt(), palavras, significado, categorias));
        }
        return pictogramas;
    }

    public byte[] baixarImagem(int id) throws IOException {
        HttpResponse<byte[]> resposta = enviar(URL_IMAGEM.formatted(id, id), HttpResponse.BodyHandlers.ofByteArray());
        if (resposta.statusCode() != 200) {
            throw new IOException("não foi possível baixar o pictograma " + id + " (código " + resposta.statusCode() + ").");
        }
        return resposta.body();
    }

    private <T> HttpResponse<T> enviar(String url, HttpResponse.BodyHandler<T> leitor) throws IOException {
        HttpRequest requisicao = HttpRequest.newBuilder(URI.create(url))
                .timeout(Duration.ofSeconds(30))
                .GET()
                .build();
        try {
            return CLIENTE.send(requisicao, leitor);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("a busca de pictogramas foi interrompida.", e);
        } catch (IOException e) {
            throw new IOException("não foi possível acessar o ARASAAC. Verifique a conexão com a internet.", e);
        }
    }

    private void adicionarSemRepetir(List<String> lista, String valor) {
        if (!valor.isBlank() && !lista.contains(valor)) lista.add(valor);
    }
}
