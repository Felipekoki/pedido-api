package com.felipecoqui.pedidoapi.resource;

import org.springframework.http.ResponseEntity;

public class ApiResponse {

    public static <T> ResponseEntity<T> ok(final T entity){
        return ResponseEntity.ok().body(entity);
    }

    private ApiResponse() {}
}