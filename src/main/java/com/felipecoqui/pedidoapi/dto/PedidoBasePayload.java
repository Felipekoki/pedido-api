package com.felipecoqui.pedidoapi.dto;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public abstract class PedidoBasePayload {

    @NotBlank(message = "O Campo Numero de Controle é obrigatório")
    protected String numeroControle;
    protected LocalDate dataCadastro;
    @NotBlank(message = "O Campo Nome do Produto é obrigatório")
    protected String nomeProduto;
    @NotNull(message = "O Campo Valor Unitário é obrigatório")
    @Min(value = 1, message = "O Campo Valor Uniário tem que possuir um valor maior que zero")
    protected double valorUnitario;
    protected int quantidade;
    @NotNull(message = "O Campo Código do Cliente é obrigatório")
    @Min(value = 1, message = "O Campo Código do Cliente tem que possuir um valor maior que zero")
    protected int codigoCliente;

    public LocalDate getDataCadastro() {
        return dataCadastro != null ? dataCadastro : LocalDate.now();
    }

    public Integer getQuantidade() {
        return quantidade == 0 ? 1 : quantidade;
    }
}
