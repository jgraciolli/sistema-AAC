package utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ImageHelper {

    public static final List<String> EXTENSOES = List.of("png", "jpg", "jpeg", "gif", "bmp");

    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    private final Path _pasta;

    public ImageHelper(String nomeArquivoPrancha) {
        _pasta = Path.of("database", "imagens", nomeArquivoPrancha);
    }

    public String gravarImagem(File origem, String nomeCard) throws IOException {
        String nomeOriginal = origem.getName();
        int ponto = nomeOriginal.lastIndexOf('.');
        String nomeImagem = ponto >= 0 ? nomeOriginal.substring(0, ponto) : nomeOriginal;
        return gravarImagem(Files.readAllBytes(origem.toPath()), nomeImagem, nomeCard, nomeOriginal);
    }

    public String gravarImagem(byte[] conteudo, String nomeImagem, String nomeCard) throws IOException {
        return gravarImagem(conteudo, nomeImagem, nomeCard, nomeImagem);
    }

    private String gravarImagem(byte[] conteudo, String nomeImagem, String nomeCard, String nomeExibicao) throws IOException {
        String extensao = detectarExtensao(conteudo, nomeExibicao);
        String nomeFinal = sanitizar(nomeImagem) + "_" + LocalDateTime.now().format(FORMATO_DATA) + "_" + nomeCard + "." + extensao;

        Files.createDirectories(_pasta);
        Path destino = _pasta.resolve(nomeFinal);
        Files.write(destino, conteudo);
        return destino.toString().replace('\\', '/');
    }

    public static void excluirImagem(String caminho) {
        if (caminho == null || caminho.isBlank()) return;
        try {
            Files.deleteIfExists(Path.of(caminho));
        } catch (IOException e) {
            System.out.println("Erro ao excluir imagem " + caminho + ": " + e.getMessage());
        }
    }

    public void excluirPasta() {
        try {
            Files.deleteIfExists(_pasta);
        } catch (IOException e) {
            System.out.println("Pasta de imagens não está vazia: " + _pasta);
        }
    }

    private String detectarExtensao(byte[] b, String nomeArquivo) throws IOException {
        int lidos = Math.min(b.length, 12);
        String ascii = new String(b, 0, lidos, StandardCharsets.ISO_8859_1);

        if (lidos >= 8 && ascii.startsWith("\u0089PNG")) return "png";
        if (lidos >= 3 && (b[0] & 0xFF) == 0xFF && (b[1] & 0xFF) == 0xD8 && (b[2] & 0xFF) == 0xFF) return "jpg";
        if (ascii.startsWith("GIF8")) return "gif";
        if (ascii.startsWith("BM")) return "bmp";
        if (lidos >= 12 && ascii.startsWith("RIFF") && ascii.startsWith("WEBP", 8)) {
            throw new IOException("a imagem \"" + nomeArquivo + "\" está no formato WebP, que o sistema não exibe.\n"
                    + "Abra a imagem e salve como PNG ou JPG (por exemplo, pelo Paint) antes de adicionar.");
        }
        throw new IOException("o arquivo \"" + nomeArquivo + "\" não é uma imagem PNG, JPG, GIF ou BMP.");
    }

    private String sanitizar(String nome) {
        String limpo = nome.replaceAll("[^A-Za-z0-9-]", "-").replaceAll("-+", "-").replaceAll("^-|-$", "");
        return limpo.isBlank() ? "imagem" : limpo;
    }
}
