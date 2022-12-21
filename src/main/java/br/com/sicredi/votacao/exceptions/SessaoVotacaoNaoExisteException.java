package br.com.sicredi.votacao.exceptions;

public class SessaoVotacaoNaoExisteException extends RuntimeException {

    public SessaoVotacaoNaoExisteException(String msg) {
        super(msg);
    }

}
