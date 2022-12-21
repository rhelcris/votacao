package br.com.sicredi.votacao.repositories;

import br.com.sicredi.votacao.modelo.Pauta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PautaRepository extends JpaRepository<Pauta, Long> {

}
