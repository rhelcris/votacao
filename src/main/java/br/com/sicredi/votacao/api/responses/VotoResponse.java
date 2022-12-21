package br.com.sicredi.votacao.api.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VotoResponse {

    private Long protocolo;

    @JsonProperty("pauta")
    private PautaResponse pautaResponse;

    private Long associadoId;
    private String voto;

}
