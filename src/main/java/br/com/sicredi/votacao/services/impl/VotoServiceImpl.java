package br.com.sicredi.votacao.services.impl;

import br.com.sicredi.votacao.api.responses.ResultadoApuracaoResponse;
import br.com.sicredi.votacao.exceptions.AssociadoJaVotouException;
import br.com.sicredi.votacao.exceptions.SessaoDeVotacaoEncerradaException;
import br.com.sicredi.votacao.exceptions.SessaoVotacaoNaoExisteException;
import br.com.sicredi.votacao.exceptions.VotoIlegalException;
import br.com.sicredi.votacao.modelo.SessaoVotacao;
import br.com.sicredi.votacao.modelo.Voto;
import br.com.sicredi.votacao.repositories.VotoRepository;
import br.com.sicredi.votacao.services.VotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class VotoServiceImpl implements VotoService {

    private final VotoRepository votoRepository;

    @Override
    public Voto registrarVoto(Voto voto) {
        validarVoto(voto);
        return votoRepository.save(voto);
    }

    @Override
    public ResultadoApuracaoResponse apurarVotos(SessaoVotacao sessaoVotacao) {
        var votos = votoRepository.findBySessaoVotacaoId(sessaoVotacao.getId());
        return realizarApuracao(votos);
    }

    private ResultadoApuracaoResponse realizarApuracao(List<Voto> votos) {
        Integer quantidadeDeSim = 0;
        Integer quantidadeDeNao = 0;

        for (Voto voto: votos) {
            if("Sim".equals(voto.getVoto())) {
                quantidadeDeSim++;
            } else {
                quantidadeDeNao++;
            }
        }

        return ResultadoApuracaoResponse.builder()
                .quantidadeVotosNao(quantidadeDeNao)
                .quantidadeVotosSim(quantidadeDeSim)
                .build();
    }

    private void validarVoto(Voto voto) {
        validarSeASessaoDeVotacaoEstaAberta(voto);
        validarOpcaoDoVoto(voto);
        validarSessaoVotacao(voto);
        validarSeAssociadoJaVotou(voto);
    }

    private void validarSeASessaoDeVotacaoEstaAberta(Voto voto) {
        if(LocalDateTime.now().isAfter(voto.getSessaoVotacao().getDataHoraEncerramento()))
            throw new SessaoDeVotacaoEncerradaException("Não é possível registrar o voto a sessão expirou o prazo");
    }

    private void validarSeAssociadoJaVotou(Voto voto) {
        var votoEncontrado = votoRepository.findBySessaoVotacaoIdAndAssociadoId(voto.getSessaoVotacao().getId(), voto.getAssociadoId());
        if(nonNull(votoEncontrado)) {
            throw new AssociadoJaVotouException("O associado: " + voto.getAssociadoId() + " já votou na sessão " + voto.getSessaoVotacao());
        }
    }

    private void validarSessaoVotacao(Voto voto) {
        if(isNull(voto.getSessaoVotacao())) {
            throw new SessaoVotacaoNaoExisteException("Não foi encontrada a Sessâo de Votação ");
        }
    }

    private void validarOpcaoDoVoto(Voto voto) {
        if( !("Sim".equals(voto.getVoto()) || "Não".equals(voto.getVoto())) ){
            throw new VotoIlegalException("Opção de voto não válida. use \"Sim\" ou \"Não\"");
        }
    }
}
