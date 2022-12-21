package br.com.sicredi.votacao.utils;

import br.com.sicredi.votacao.modelo.SessaoVotacao;

import java.time.LocalDateTime;

import static br.com.sicredi.votacao.utils.PautaUtils.umaPauta;

public class SessaoVotacaoUtils {

    public static SessaoVotacao umaSessaoDeVotacao(LocalDateTime dataHora, int quantidadeMinutosParaEncerramento) {
        return SessaoVotacao.builder()
                .id(1l)
                .pauta(umaPauta())
                .dataHoraInicio(dataHora)
                .dataHoraEncerramento(dataHora.plusMinutes(quantidadeMinutosParaEncerramento))
                .build();
    }

}
