package utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
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
        String extensao = detectarExtensao(origem);

        String nomeOriginal = origem.getName();
        int ponto = nomeOriginal.lastIndexOf('.');
        String nomeImagem = sanitizar(ponto >= 0 ? nomeOriginal.substring(0, ponto) : nomeOriginal);
        String nomeFinal = nomeImagem + "_" + LocalDateTime.now().format(FORMATO_DATA) + "_" + nomeCard + "." + extensao;

        Files.createDirectories(_pasta);
        Path destino = _pasta.resolve(nomeFinal);
        Files.copy(origem.toPath(), destino, StandardCopyOption.REPLACE_EXISTING);
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

    private String detectarExtensao(File arquivo) throws IOException {
        byte[] b = new byte[12];
        int lidos;
        try (InputStream entrada = Files.newInputStream(arquivo.toPath())) {
            lidos = entrada.readNBytes(b, 0, b.length);
        }
        String ascii = new String(b, 0, lidos, StandardCharsets.ISO_8859_1);

        if (lidos >= 8 && ascii.startsWith("\u0089PNG")) return "png";
        if (lidos >= 3 && (b[0] & 0xFF) == 0xFF && (b[1] & 0xFF) == 0xD8 && (b[2] & 0xFF) == 0xFF) return "jpg";
        if (ascii.startsWith("GIF8")) return "gif";
        if (ascii.startsWith("BM")) return "bmp";
        if (lidos >= 12 && ascii.startsWith("RIFF") && ascii.startsWith("WEBP", 8)) {
            throw new IOException("a imagem \"" + arquivo.getName() + "\" está no formato WebP, que o sistema não exibe.\n"
                    + "Abra a imagem e salve como PNG ou JPG (por exemplo, pelo Paint) antes de adicionar.");
        }
        throw new IOException("o arquivo \"" + arquivo.getName() + "\" não é uma imagem PNG, JPG, GIF ou BMP.");
    }

    private String sanitizar(String nome) {
        String limpo = nome.replaceAll("[^A-Za-z0-9-]", "-").replaceAll("-+", "-").replaceAll("^-|-$", "");
        return limpo.isBlank() ? "imagem" : limpo;
    }
}
