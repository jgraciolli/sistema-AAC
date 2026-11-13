package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.ollama4j.Ollama;
import io.github.ollama4j.exceptions.OllamaException;
import io.github.ollama4j.models.generate.OllamaGenerateRequest;
import io.github.ollama4j.models.request.ThinkMode;
import io.github.ollama4j.models.response.OllamaResult;
import io.github.ollama4j.utils.OptionsBuilder;

import java.io.IOException;
import java.util.Map;

public class OllamaHelper {

    private static final String HOST = configuracao("AAC_OLLAMA_HOST", "http://localhost:11434/");
    private static final String MODELO = configuracao("AAC_OLLAMA_MODELO", "qwen3.5:4b");
    private static final ObjectMapper JSON = new ObjectMapper();
    private static OllamaHelper _instancia;

    private final Ollama _ollama;
    private boolean _modeloPronto;

    private OllamaHelper() {
        _ollama = new Ollama(HOST);
        _ollama.setRequestTimeoutSeconds(120);
    }

    public static synchronized OllamaHelper instancia() {
        if (_instancia == null) _instancia = new OllamaHelper();
        return _instancia;
    }

    public JsonNode perguntar(String prompt, Map<String, Object> formatoResposta) throws IOException {
        prepararModelo();
        OllamaGenerateRequest requisicao = OllamaGenerateRequest.builder()
                .withModel(MODELO)
                .withPrompt(prompt)
                .withFormat(formatoResposta)
                .withThink(ThinkMode.DISABLED)
                .withOptions(new OptionsBuilder().setTemperature(0f).build())
                .build();
        try {
            OllamaResult resultado = _ollama.generate(requisicao, null);
            return JSON.readTree(resultado.getResponse());
        } catch (OllamaException e) {
            throw new IOException("falha ao consultar o modelo " + MODELO + " no Ollama: " + e.getMessage(), e);
        }
    }

    private synchronized void prepararModelo() throws IOException {
        if (_modeloPronto) return;
        try {
            boolean instalado = _ollama.listModels().stream()
                    .anyMatch(m -> m.getName().equals(MODELO) || m.getName().equals(MODELO + ":latest"));
            if (!instalado) {
                System.out.println("Baixando o modelo " + MODELO + " no Ollama. Isso pode demorar alguns minutos...");
                _ollama.pullModel(MODELO);
            }
            _modeloPronto = true;
        } catch (OllamaException e) {
            throw new IOException("o Ollama não está acessível em " + HOST + ". Abra o Ollama e tente de novo.", e);
        }
    }

    private static String configuracao(String variavel, String padrao) {
        String valor = System.getenv(variavel);
        return valor == null || valor.isBlank() ? padrao : valor;
    }
}
