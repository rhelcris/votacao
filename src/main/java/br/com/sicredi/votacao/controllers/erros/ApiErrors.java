package br.com.sicredi.votacao.controllers.erros;

import br.com.sicredi.votacao.exceptions.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ApiErrors {

    private final List<String> errors;

    public ApiErrors(BindingResult bindingResult) {
        this.errors = new ArrayList<>();
        bindingResult.getAllErrors().forEach( error -> this.errors.add(error.getDefaultMessage()) );
    }

    public ApiErrors(PautaNaoExisteException ex) {
        this.errors = Arrays.asList(ex.getMessage());
    }
    public ApiErrors(AssociadoJaVotouException ex) {
        this.errors = Arrays.asList(ex.getMessage());
    }
    public ApiErrors(SessaoVotacaoNaoExisteException ex) {
        this.errors = Arrays.asList(ex.getMessage());
    }
    public ApiErrors(VotoIlegalException ex) {
        this.errors = Arrays.asList(ex.getMessage());
    }
    public ApiErrors(SessaoDeVotacaoEncerradaException ex) {
        this.errors = Arrays.asList(ex.getMessage());
    }

    public ApiErrors(ResponseStatusException ex) {
        this.errors = Arrays.asList(ex.getReason());
    }

    public List<String> getErrors() {
        return errors;
    }

}
