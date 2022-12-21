package br.com.sicredi.votacao.services.impl;

import br.com.sicredi.votacao.adapter.PautaAdapter;
import br.com.sicredi.votacao.api.requests.PautaRequest;
import br.com.sicredi.votacao.api.responses.PautaResponse;
import br.com.sicredi.votacao.exceptions.PautaNaoExisteException;
import br.com.sicredi.votacao.modelo.Pauta;
import br.com.sicredi.votacao.repositories.PautaRepository;
import br.com.sicredi.votacao.services.PautaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PautaServiceImpl implements PautaService {

    private final PautaRepository pautaRepository;

    @Override
    public PautaResponse criarPauta(PautaRequest pautaRequest) {
        log.info("Iniciando a gravação da pauta: " + pautaRequest.getDescricao());
        Pauta pauta = PautaAdapter.adapter(pautaRequest);
        pautaRepository.save(pauta);
        log.info("Pauta: " + pautaRequest.getDescricao() + " gravada com sucesso.");
        return PautaAdapter.adapter(pauta);
    }

    @Override
    public Pauta buscarPeloCodigo(Long pautaId) {
        return pautaRepository
                .findById(pautaId)
                .orElseThrow(() -> new PautaNaoExisteException("Pauta nao encontrada com codigo: " + pautaId));
    }

}
