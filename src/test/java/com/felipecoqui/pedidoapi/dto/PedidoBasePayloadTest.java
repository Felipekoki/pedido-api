package com.felipecoqui.pedidoapi.dto;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class PedidoBasePayloadTest {

    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();

    private static class TestablePedidoBasePayload extends PedidoBasePayload {
        // Criar um construtor que inicializa os campos necessários
        public TestablePedidoBasePayload(String numeroControle, String nomeProduto, double valorUnitario, int codigoCliente) {
            this.numeroControle = numeroControle;
            this.nomeProduto = nomeProduto;
            this.valorUnitario = valorUnitario;
            this.codigoCliente = codigoCliente;
        }

    }

    @Test
    void dadoNumeroControleEmBranco_deveTerViolacao() {
        // Dado
        TestablePedidoBasePayload payload = new TestablePedidoBasePayload("", "Produto", 10.0, 1);

        // Quando
        Set<ConstraintViolation<TestablePedidoBasePayload>> violations = validator.validate(payload);

        // Então
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("O Campo Numero de Controle é obrigatório");
    }

    @Test
    void dadoDataCadastroNula_deveDefinirDataAtual() {
        // Dado
        TestablePedidoBasePayload payload = new TestablePedidoBasePayload("123", "Produto", 10.0, 1);

        // Quando
        Set<ConstraintViolation<TestablePedidoBasePayload>> violations = validator.validate(payload);

        // Então
        assertThat(violations).isEmpty();
        assertThat(payload.getDataCadastro()).isNotNull();
    }

    @Test
    void dadoCodigoClienteZero_deveTerViolacao() {
        // Dado
        TestablePedidoBasePayload payload = new TestablePedidoBasePayload("123", "Produto", 10.0, 0);

        // Quando
        Set<ConstraintViolation<TestablePedidoBasePayload>> violations = validator.validate(payload);

        // Então
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("O Campo Código do Cliente tem que possuir um valor maior que zero");
    }

    @Test
    void dadoValorUnitarioNegativo_deveTerViolacao() {
        // Dado
        TestablePedidoBasePayload payload = new TestablePedidoBasePayload("123", "Produto", -10.0, 1);

        // Quando
        Set<ConstraintViolation<TestablePedidoBasePayload>> violations = validator.validate(payload);

        // Então
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("O Campo Valor Uniário tem que possuir um valor maior que zero");
    }
}
