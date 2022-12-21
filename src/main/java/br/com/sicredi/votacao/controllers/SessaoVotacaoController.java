package br.com.sicredi.votacao.controllers;

import br.com.sicredi.votacao.api.requests.SessaoVotacaoRequest;
import br.com.sicredi.votacao.api.requests.VotoRequest;
import br.com.sicredi.votacao.api.responses.ResultadoApuracaoResponse;
import br.com.sicredi.votacao.api.responses.SessaoVotacaoResponse;
import br.com.sicredi.votacao.api.responses.VotoResponse;
import br.com.sicredi.votacao.services.SessaoVotacaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RestController
@RequestMapping("sessoes_votacao")
@RequiredArgsConstructor
public class SessaoVotacaoController {

    private final SessaoVotacaoService sessaoVotacaoService;

    @PostMapping
    public ResponseEntity<SessaoVotacaoResponse> abrirSessaoDeVotacao(@RequestBody SessaoVotacaoRequest sessaoVotacaoRequest) {
        log.info("Solicitacao da abertura da sessao de votacao recebida. " + sessaoVotacaoRequest);
        SessaoVotacaoResponse sessaoVotacaoResponse = sessaoVotacaoService.abrirSessaoDeVotacao(sessaoVotacaoRequest);
        return ResponseEntity.status(CREATED).body(sessaoVotacaoResponse);
    }

    @PostMapping("/{sessao_votacao_id}/votar/{pauta_id}")
    public ResponseEntity<?> votar(@PathVariable("sessao_votacao_id") Long sessaoVotacaoId,
                                   @PathVariable("pauta_id") Long pautaId,
                                   @RequestBody VotoRequest votoRequest) {
        log.info("Recebendo o voto do associado: " + votoRequest.getAssociadoId() + ", na sessão: " + sessaoVotacaoId
                + ", na pauta: " + pautaId + ", com o Voto. " + votoRequest);
        VotoResponse votoResponse = sessaoVotacaoService.registrarVoto(sessaoVotacaoId, pautaId, votoRequest);
        return ResponseEntity.status(CREATED).body(votoResponse);
    }

    @GetMapping("/{sessao_votacao_id}/apurar/{pauta_id}")
    public ResponseEntity<ResultadoApuracaoResponse> apurarVotacao(@PathVariable("sessao_votacao_id") Long sessaoVotacaoId,
                                              @PathVariable("pauta_id") Long pautaId) {
        log.info("Iniciando a apuração dos votos na Sessão: " + sessaoVotacaoId + ", da pauta: " + pautaId);
        ResultadoApuracaoResponse resultadoApuracao = sessaoVotacaoService.apurarVotacao(sessaoVotacaoId, pautaId);
        log.info("Apuracao da Sessão: " + sessaoVotacaoId + ", pauta: " + pautaId + " foi realizada com sucesso. Total de votos: " + resultadoApuracao);
        return ResponseEntity.status(OK).body(resultadoApuracao);
    }

}
