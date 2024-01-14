package com.felipecoqui.pedidoapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoPayload extends PedidoBasePayload{
    private Integer id;
    private double valorTotal;
}
