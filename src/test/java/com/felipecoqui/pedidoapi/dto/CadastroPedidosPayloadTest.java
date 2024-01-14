package com.felipecoqui.pedidoapi.dto;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class CadastroPedidosPayloadTest {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    void dadoListaPedidosNula_deveTerViolacao() {
        CadastroPedidosPayload payload = new CadastroPedidosPayload(null);
        Set<ConstraintViolation<CadastroPedidosPayload>> violacoes = validator.validate(payload);
        assertThat(violacoes).hasSize(1);
        assertThat(violacoes.iterator().next().getMessage()).isEqualTo("Deve possuir uma lista de pedidos");
    }

    @Test
    void dadoListaPedidosVazia_deveTerViolacao() {
        CadastroPedidosPayload payload = new CadastroPedidosPayload(new ArrayList<>());
        Set<ConstraintViolation<CadastroPedidosPayload>> violacoes = validator.validate(payload);
        assertThat(violacoes).hasSize(1);
        assertThat(violacoes.iterator().next().getMessage()).isEqualTo("A lista de pedidos deve conter de 1 a 10 elementos");
    }

    @Test
    void dadoMuitosPedidos_deveTerViolacao() {
        List<CadastroPedidoPayload> pedidos = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            pedidos.add(criarCadastroPedidoPayload());
        }
        CadastroPedidosPayload payload = new CadastroPedidosPayload(pedidos);
        Set<ConstraintViolation<CadastroPedidosPayload>> violacoes = validator.validate(payload);
        assertThat(violacoes).hasSize(1);
        assertThat(violacoes.iterator().next().getMessage()).isEqualTo("A lista de pedidos deve conter de 1 a 10 elementos");
    }

    @Test
    void dadoListaPedidosValida_naoDeveTerViolacoes() {
        List<CadastroPedidoPayload> pedidos = new ArrayList<>();
        pedidos.add(criarCadastroPedidoPayload());
        CadastroPedidosPayload payload = new CadastroPedidosPayload(pedidos);
        Set<ConstraintViolation<CadastroPedidosPayload>> violacoes = validator.validate(payload);
        assertThat(violacoes).isEmpty();
    }

    @Test
    void dadoListaPedidosComElementoNulo_deveTerViolacao() {
        List<CadastroPedidoPayload> pedidos = new ArrayList<>();
        pedidos.add(null);
        CadastroPedidosPayload payload = new CadastroPedidosPayload(pedidos);
        Set<ConstraintViolation<CadastroPedidosPayload>> violacoes = validator.validate(payload);
        assertThat(violacoes).hasSize(1);
        assertThat(violacoes.iterator().next().getMessage()).isEqualTo("A lista de pedidos não pode conter elementos nulos");
    }

    @Test
    void dadoListaPedidosComElementosValidosENulos_deveTerViolacoes() {
        List<CadastroPedidoPayload> pedidos = new ArrayList<>();
        pedidos.add(criarCadastroPedidoPayload());
        pedidos.add(null);
        CadastroPedidosPayload payload = new CadastroPedidosPayload(pedidos);
        Set<ConstraintViolation<CadastroPedidosPayload>> violacoes = validator.validate(payload);
        assertThat(violacoes).hasSize(1);
        assertThat(violacoes.iterator().next().getMessage()).isEqualTo("A lista de pedidos não pode conter elementos nulos");
    }

    private CadastroPedidoPayload criarCadastroPedidoPayload() {
        CadastroPedidoPayload pedido = new CadastroPedidoPayload();
        pedido.setNumeroControle("ABC123");
        pedido.setNomeProduto("Produto1");
        pedido.setCodigoCliente(1);
        pedido.setDataCadastro(LocalDate.now());
        pedido.setValorUnitario(3.96);
        pedido.setQuantidade(2);
        return pedido;
    }
}
