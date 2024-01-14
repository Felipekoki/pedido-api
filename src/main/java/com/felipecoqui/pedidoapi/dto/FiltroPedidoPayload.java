package com.felipecoqui.pedidoapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FiltroPedidoPayload {
    private Integer id;
    private String numeroControle;
    private LocalDate dataCadastro;
    private int pagina = 0;
    private int tamanho = 10;
}
