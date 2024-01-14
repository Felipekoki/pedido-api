package com.felipecoqui.pedidoapi.exceptions;

public class NaoEncontradoException extends Exception{
    private static final long serialVersionUID = 2099071805422576270L;

    public NaoEncontradoException(String message){
        super(message);
    }
}
