package br.com.sicredi.votacao.services;

import br.com.sicredi.votacao.api.responses.ResultadoApuracaoResponse;
import br.com.sicredi.votacao.modelo.SessaoVotacao;
import br.com.sicredi.votacao.modelo.Voto;

public interface VotoService {

    Voto registrarVoto(Voto voto);

    ResultadoApuracaoResponse apurarVotos(SessaoVotacao sessaoVotacao);
}
