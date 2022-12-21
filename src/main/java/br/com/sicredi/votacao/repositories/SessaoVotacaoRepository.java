package br.com.sicredi.votacao.repositories;

import br.com.sicredi.votacao.modelo.SessaoVotacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessaoVotacaoRepository extends JpaRepository<SessaoVotacao, Long> {

    SessaoVotacao findByIdAndPautaId(Long id, Long pautaId);

}
