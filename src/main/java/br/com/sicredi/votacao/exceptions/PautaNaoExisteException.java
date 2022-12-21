package br.com.sicredi.votacao.exceptions;

public class PautaNaoExisteException extends RuntimeException {

    public PautaNaoExisteException(String mensagem) {
        super(mensagem);
    }

}
