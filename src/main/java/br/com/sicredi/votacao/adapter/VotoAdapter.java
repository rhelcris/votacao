package br.com.sicredi.votacao.adapter;

import br.com.sicredi.votacao.api.responses.VotoResponse;
import br.com.sicredi.votacao.modelo.Voto;

public class VotoAdapter {

    public static VotoResponse adapter(Voto voto) {
        return VotoResponse.builder()
                .protocolo(voto.getId())
                .associadoId(voto.getAssociadoId())
                .pautaResponse(PautaAdapter.adapter(voto.getSessaoVotacao().getPauta()))
                .voto(voto.getVoto())
                .build();
    }

}
