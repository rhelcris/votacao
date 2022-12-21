package br.com.sicredi.votacao.adapter;

import br.com.sicredi.votacao.api.requests.PautaRequest;
import br.com.sicredi.votacao.api.responses.PautaResponse;
import br.com.sicredi.votacao.modelo.Pauta;

public class PautaAdapter {

    public static Pauta adapter(PautaRequest pautaRequest) {
        return Pauta.builder().descricao(pautaRequest.getDescricao()).build();
    }

    public static PautaResponse adapter(Pauta pauta) {
        return PautaResponse.builder().id(pauta.getId()).descricao(pauta.getDescricao()).build();
    }
}
