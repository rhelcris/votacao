package br.com.sicredi.votacao.api.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResultadoApuracaoResponse {

    @JsonProperty("quantidade_votos_sim")
    int quantidadeVotosSim;

    @JsonProperty("quantidade_votos_nao")
    int quantidadeVotosNao;

}
