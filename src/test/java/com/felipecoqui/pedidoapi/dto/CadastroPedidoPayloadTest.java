package com.felipecoqui.pedidoapi.dto;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class CadastroPedidoPayloadTest {

    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();

    @Test
    void dadoNumeroControleEmBranco_deveTerViolacao() {
        CadastroPedidoPayload pedido = new CadastroPedidoPayload();
        pedido.setNumeroControle("");
        pedido.setNomeProduto("Produto1");
        pedido.setCodigoCliente(1);
        pedido.setQuantidade(2);
        pedido.setValorUnitario(1);
        Set<ConstraintViolation<CadastroPedidoPayload>> violations = validator.validate(pedido);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("O Campo Numero de Controle é obrigatório");
    }

    @Test
    void dadoValorUnitarioNegativo_deveTerViolacao() {
        CadastroPedidoPayload pedido = new CadastroPedidoPayload();
        pedido.setNumeroControle("ABC123");
        pedido.setNomeProduto("Produto1");
        pedido.setCodigoCliente(1);
        pedido.setQuantidade(2);
        pedido.setValorUnitario(-1.0);
        Set<ConstraintViolation<CadastroPedidoPayload>> violations = validator.validate(pedido);

        // Então
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("O Campo Valor Uniário tem que possuir um valor maior que zero");
    }

    @Test
    void dadoCodigoClienteZero_deveTerViolacao() {
        CadastroPedidoPayload pedido = new CadastroPedidoPayload();
        pedido.setNumeroControle("ABC123");
        pedido.setNomeProduto("Produto1");
        pedido.setCodigoCliente(0);
        pedido.setQuantidade(2);
        pedido.setValorUnitario(1.0);
        Set<ConstraintViolation<CadastroPedidoPayload>> violations = validator.validate(pedido);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("O Campo Código do Cliente tem que possuir um valor maior que zero");
    }
}

