package br.com.sicredi.votacao.api.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PautaResponse {

    @JsonProperty("pauta_codigo")
    private Long id;

    @JsonProperty("pauta_descricao")
    private String descricao;

}
