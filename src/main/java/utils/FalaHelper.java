package utils;

import com.sun.jna.platform.win32.COM.COMLateBindingObject;
import com.sun.jna.platform.win32.COM.COMUtils;
import com.sun.jna.platform.win32.Ole32;
import com.sun.jna.platform.win32.Variant;
import interfaces.IFala;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//NOTE: text to speech pelo sintetizador do Windows (SAPI) via JNA.
// Pede uma voz em português (pt-BR, ex.: "Microsoft Maria"); se não houver, usa a voz padrão.
public class FalaHelper implements IFala {

    private static final int SAPI_TEXTO_XML = 8; // SVSFIsXML
    private static FalaHelper _instancia;

    // SAPI é COM: todas as falas rodam numa única thread própria, sem travar a tela
    private final ExecutorService _thread = Executors.newSingleThreadExecutor(r -> {
        Thread t = new Thread(r, "fala");
        t.setDaemon(true);
        return t;
    });
    private SpVoice _voz;

    public static synchronized FalaHelper instancia() {
        if (_instancia == null) _instancia = new FalaHelper();
        return _instancia;
    }

    private FalaHelper() {}

    public void falar(String texto) {
        if (texto == null || texto.isBlank()) return;
        _thread.submit(() -> {
            try {
                if (_voz == null) {
                    COMUtils.checkRC(Ole32.INSTANCE.CoInitializeEx(null, Ole32.COINIT_APARTMENTTHREADED));
                    _voz = new SpVoice();
                }
                _voz.falar(texto);
            } catch (Exception e) {
                System.out.println("Erro ao reproduzir fala: " + e.getMessage());
            }
        });
    }

    private static class SpVoice extends COMLateBindingObject {
        SpVoice() {
            super("SAPI.SpVoice", false);
        }

        void falar(String texto) {
            // 416 = código de idioma do português do Brasil
            String xml = "<voice required=\"Language=416\">" + escaparXml(texto) + "</voice>";
            invokeNoReply("Speak", new Variant.VARIANT(xml), new Variant.VARIANT(SAPI_TEXTO_XML));
        }

        private static String escaparXml(String texto) {
            return texto.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;");
        }
    }
}
