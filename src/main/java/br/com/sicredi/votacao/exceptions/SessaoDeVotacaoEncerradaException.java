package br.com.sicredi.votacao.exceptions;

public class SessaoDeVotacaoEncerradaException extends RuntimeException {
    public SessaoDeVotacaoEncerradaException(String msg) {
        super(msg);
    }
}
