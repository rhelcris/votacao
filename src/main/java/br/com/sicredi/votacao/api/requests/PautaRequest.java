package br.com.sicredi.votacao.api.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PautaRequest {

    @JsonProperty("pauta_descricao")
    private String descricao;

}
