package br.com.sicredi.votacao.exceptions;

public class VotoIlegalException extends IllegalArgumentException {

    public VotoIlegalException(String msg) {
        super(msg);
    }

}
