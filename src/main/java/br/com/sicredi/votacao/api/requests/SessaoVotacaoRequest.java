package br.com.sicredi.votacao.api.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import static java.util.Objects.isNull;

@Data
@Builder
public class SessaoVotacaoRequest {

    @JsonProperty("pauta_codigo")
    private Long pautaId;

    @JsonProperty("prazo_encerramento_em_minutos")
    private Long prazoEncerramentoEmMinutos;

    public Long getPrazoEncerramentoEmMinutos() {
        return isNull(prazoEncerramentoEmMinutos) ? 1l : prazoEncerramentoEmMinutos;
    }

}
