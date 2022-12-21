package br.com.sicredi.votacao.adapter;

import br.com.sicredi.votacao.api.responses.SessaoVotacaoResponse;
import br.com.sicredi.votacao.modelo.SessaoVotacao;

public class SessaoVotacaoAdapter {

    public static SessaoVotacaoResponse adapter(SessaoVotacao sessaoVotacao) {
        return SessaoVotacaoResponse.builder()
                .codigo(sessaoVotacao.getId())
                .pautaResponse(PautaAdapter.adapter(sessaoVotacao.getPauta()))
                .dataHoraInicio(sessaoVotacao.getDataHoraInicio())
                .dataHoraEncerramento(sessaoVotacao.getDataHoraEncerramento())
                .build();
    }
}
