package br.com.sicredi.votacao.controllers.erros;

import br.com.sicredi.votacao.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(PautaNaoExisteException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handlePautaNaoExisteException(PautaNaoExisteException ex) {
        return new ApiErrors(ex);
    }

    @ExceptionHandler(AssociadoJaVotouException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleAssociadoJaVotouException(AssociadoJaVotouException ex) {
        return new ApiErrors(ex);
    }

    @ExceptionHandler(SessaoVotacaoNaoExisteException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handleSessaoVotacaoNaoExisteException(SessaoVotacaoNaoExisteException ex) {
        return new ApiErrors(ex);
    }

    @ExceptionHandler(SessaoDeVotacaoEncerradaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleSessaoDeVotacaoEncerradaException(SessaoDeVotacaoEncerradaException ex) {
        return new ApiErrors(ex);
    }

    @ExceptionHandler(VotoIlegalException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleVotoIlegalException(VotoIlegalException ex) {
        return new ApiErrors(ex);
    }

}
