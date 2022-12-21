package br.com.sicredi.votacao.services;

import br.com.sicredi.votacao.api.requests.SessaoVotacaoRequest;
import br.com.sicredi.votacao.api.requests.VotoRequest;
import br.com.sicredi.votacao.api.responses.ResultadoApuracaoResponse;
import br.com.sicredi.votacao.api.responses.SessaoVotacaoResponse;
import br.com.sicredi.votacao.api.responses.VotoResponse;

public interface SessaoVotacaoService {

    SessaoVotacaoResponse abrirSessaoDeVotacao(SessaoVotacaoRequest sessaoVotacaoRequest);

    VotoResponse registrarVoto(Long sessaoVotacaoId, Long pautaId, VotoRequest votoRequest);

    ResultadoApuracaoResponse apurarVotacao(Long sessaoVotacaoId, Long pautaId);
}
