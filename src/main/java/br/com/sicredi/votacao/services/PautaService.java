package br.com.sicredi.votacao.services;

import br.com.sicredi.votacao.api.requests.PautaRequest;
import br.com.sicredi.votacao.api.responses.PautaResponse;
import br.com.sicredi.votacao.modelo.Pauta;

public interface PautaService {

    PautaResponse criarPauta(PautaRequest pautaRequest);

    Pauta buscarPeloCodigo(Long pautaId);

}
