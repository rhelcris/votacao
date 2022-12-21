package br.com.sicredi.votacao.controllers;

import br.com.sicredi.votacao.api.requests.PautaRequest;
import br.com.sicredi.votacao.api.responses.PautaResponse;
import br.com.sicredi.votacao.services.PautaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
@RestController
@RequestMapping("pautas")
@RequiredArgsConstructor
public class PautaController {

    private final PautaService pautaService;

    @PostMapping
    public ResponseEntity<PautaResponse> criarPauta(@RequestBody PautaRequest pautaRequest) {
        log.info("Iniciando a criação da pauta: " + pautaRequest.getDescricao());
        PautaResponse pautaResponse = pautaService.criarPauta(pautaRequest);
        log.info("Pauta criada com sucesso.");
        return ResponseEntity.status(CREATED).body(pautaResponse);
    }

}
