package br.com.sicredi.votacao.utils;

import br.com.sicredi.votacao.modelo.Pauta;

public class PautaUtils {

    public static Pauta umaPauta() {
        return Pauta.builder()
                .id(1l)
                .descricao("Pauta 1")
                .build();
    }

}
