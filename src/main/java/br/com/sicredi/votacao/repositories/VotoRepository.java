package br.com.sicredi.votacao.repositories;

import br.com.sicredi.votacao.modelo.Voto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VotoRepository extends JpaRepository<Voto, Long> {

    Voto findBySessaoVotacaoIdAndAssociadoId(Long id, Long associadoId);

    List<Voto> findBySessaoVotacaoId(Long pautaId);

}
