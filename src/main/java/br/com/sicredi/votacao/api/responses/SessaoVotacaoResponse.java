package br.com.sicredi.votacao.api.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class SessaoVotacaoResponse {

    @JsonProperty("codigo")
    private Long codigo;

    @JsonProperty("pauta")
    private PautaResponse pautaResponse;

    @JsonProperty("data_hora_inicio")
    private LocalDateTime dataHoraInicio;

    @JsonProperty("data_hora_encerramento")
    private LocalDateTime dataHoraEncerramento;

}
