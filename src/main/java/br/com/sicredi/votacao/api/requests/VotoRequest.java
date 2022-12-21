package br.com.sicredi.votacao.api.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VotoRequest {

    @JsonProperty("associado_id")
    private Long associadoId;

    @JsonProperty("voto")
    private String voto;

}
