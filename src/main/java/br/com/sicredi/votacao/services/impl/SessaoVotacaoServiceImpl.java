package br.com.sicredi.votacao.services.impl;

import br.com.sicredi.votacao.adapter.SessaoVotacaoAdapter;
import br.com.sicredi.votacao.adapter.VotoAdapter;
import br.com.sicredi.votacao.api.requests.SessaoVotacaoRequest;
import br.com.sicredi.votacao.api.requests.VotoRequest;
import br.com.sicredi.votacao.api.responses.ResultadoApuracaoResponse;
import br.com.sicredi.votacao.api.responses.SessaoVotacaoResponse;
import br.com.sicredi.votacao.api.responses.VotoResponse;
import br.com.sicredi.votacao.exceptions.SessaoVotacaoNaoExisteException;
import br.com.sicredi.votacao.modelo.SessaoVotacao;
import br.com.sicredi.votacao.modelo.Voto;
import br.com.sicredi.votacao.repositories.SessaoVotacaoRepository;
import br.com.sicredi.votacao.services.PautaService;
import br.com.sicredi.votacao.services.SessaoVotacaoService;
import br.com.sicredi.votacao.services.VotoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static java.util.Objects.nonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class SessaoVotacaoServiceImpl implements SessaoVotacaoService {

    private final VotoService votoService;
    private final PautaService pautaService;
    private final SessaoVotacaoRepository sessaoVotacaoRepository;

    @Override
    public SessaoVotacaoResponse abrirSessaoDeVotacao(SessaoVotacaoRequest sessaoVotacaoRequest) {
        log.info("Iniciando a abertura da sessao de votacao" + sessaoVotacaoRequest);
        var sessaoVotacao = buildSessaoVotacao(sessaoVotacaoRequest);
        sessaoVotacaoRepository.save(sessaoVotacao);
        log.info("Sessao de votacao aberta com sucesso. " + sessaoVotacao);
        return SessaoVotacaoAdapter.adapter(sessaoVotacao);

    }

    @Override
    public VotoResponse registrarVoto(Long sessaoVotacaoId, Long pautaId, VotoRequest votoRequest) {
        log.info("Iniciando o registro do voto do associado: " + votoRequest.getAssociadoId() + ", na sessão: "
                + sessaoVotacaoId + ", na pauta: " + pautaId + ", com o Voto. " + votoRequest.getVoto());
        SessaoVotacao sessaoVotacao = buscarSessaoDeVotacaoPelaPauta(sessaoVotacaoId, pautaId);
        if(nonNull(sessaoVotacao)) {
            var voto = buildVoto(sessaoVotacao, votoRequest);
            votoService.registrarVoto(voto);
            log.info("Voto registrado com sucesso. " + voto);
            return VotoAdapter.adapter(voto);
        } else {
            log.error("A sessao de votacao da pauta: " + pautaId + " não existe");
            throw new SessaoVotacaoNaoExisteException("A sessao de votacao da pauta: " + pautaId + " não existe");
        }
    }

    @Override
    public ResultadoApuracaoResponse apurarVotacao(Long sessaoVotacaoId, Long pautaId) {
        SessaoVotacao sessaoVotacao = buscarSessaoDeVotacaoPelaPauta(sessaoVotacaoId, pautaId);
        if(nonNull(sessaoVotacao)) {
            return votoService.apurarVotos(sessaoVotacao);
        } else {
            log.error("A sessao de votacao da pauta: " + pautaId + " não existe");
            throw new SessaoVotacaoNaoExisteException("A sessao de votacao da pauta: " + pautaId + " não existe");
        }
    }

    private Voto buildVoto(SessaoVotacao sessaoVotacao, VotoRequest votoRequest) {
        return Voto.builder()
                .sessaoVotacao(sessaoVotacao)
                .associadoId(votoRequest.getAssociadoId())
                .voto(votoRequest.getVoto())
                .build();
    }

    private SessaoVotacao buscarSessaoDeVotacaoPelaPauta(Long sessaoVotacaoId, Long pautaId) {
        return sessaoVotacaoRepository.findByIdAndPautaId(sessaoVotacaoId, pautaId);
    }

    private SessaoVotacao buildSessaoVotacao(SessaoVotacaoRequest sessaoVotacaoRequest) {
        var dataHoraInicio = LocalDateTime.now();
        return SessaoVotacao.builder()
                .pauta(pautaService.buscarPeloCodigo(sessaoVotacaoRequest.getPautaId()))
                .dataHoraInicio(dataHoraInicio)
                .dataHoraEncerramento(dataHoraInicio.plusMinutes(sessaoVotacaoRequest.getPrazoEncerramentoEmMinutos()))
                .build();
    }
}
