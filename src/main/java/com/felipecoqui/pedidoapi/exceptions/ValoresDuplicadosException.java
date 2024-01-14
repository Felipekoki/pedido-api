package com.felipecoqui.pedidoapi.exceptions;

public class ValoresDuplicadosException extends Exception{

    private static final long serialVersionUID = 2099032805423776270L;

    public ValoresDuplicadosException(String message){
        super(message);
    }
}
