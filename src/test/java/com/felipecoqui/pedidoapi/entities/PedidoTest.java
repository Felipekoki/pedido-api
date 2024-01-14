package com.felipecoqui.pedidoapi.entities;

import com.felipecoqui.pedidoapi.repositories.PedidoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class PedidoTest {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Test
    void savePedido_ShouldPersistPedidoInDatabase() {
        // Given
        Cliente cliente = new Cliente();
        cliente.setNomeCliente("Nome do Cliente");
        Pedido pedido = new Pedido("789", LocalDate.now(), "Produto", 10.0, 2, cliente);

        // When
        Pedido savedPedido = pedidoRepository.save(pedido);

        // Then
        assertNotNull(savedPedido.getId());
        assertEquals(pedido.getNumeroControle(), savedPedido.getNumeroControle());
    }

    @Test
    void calcularValorTotal_ShouldCalculateCorrectValorTotal() {
        // Given
        Cliente cliente = new Cliente();
        cliente.setNomeCliente("Nome do Cliente");
        Pedido pedido = new Pedido("123", LocalDate.now(), "Produto", 10.0, 3, cliente);

        // When
        pedido.calcularValorTotal();

        // Then
        assertEquals(30.0, pedido.getValorTotal());
    }

    @Test
    void calcularValorTotalComDesconto_DeveAplicarDescontoParaQuantidadesMenorQue5() {
        // Given
        Cliente cliente = new Cliente();
        cliente.setNomeCliente("Nome do Cliente");
        Pedido pedido = new Pedido("123", LocalDate.now(), "Produto", 10.0, 4, cliente);

        // When
        pedido.calcularValorTotal();

        // Then
        assertEquals(40.0, pedido.getValorTotal());
    }

    @Test
    void calcularValorTotalComDesconto_DeveAplicarDescontoDe10PorcentoParaQuantidadesEntre5E9() {
        // Given
        Cliente cliente = new Cliente();
        cliente.setNomeCliente("Nome do Cliente");
        Pedido pedido = new Pedido("123", LocalDate.now(), "Produto", 10.0, 6, cliente);

        // When
        pedido.calcularValorTotal();

        // Then
        assertEquals(57.0, pedido.getValorTotal());
    }

    @Test
    void calcularValorTotalComDesconto_DeveAplicarDescontoParaQuantidadesMaioresQue9() {
        // Given
        Cliente cliente = new Cliente();
        cliente.setNomeCliente("Nome do Cliente");
        Pedido pedido = new Pedido("123", LocalDate.now(), "Produto", 10.0, 10, cliente);

        // When
        pedido.calcularValorTotal();

        // Then
        assertEquals(90.0, pedido.getValorTotal());
    }

    @Test
    void todosGetsESets() {
        Pedido pedido = new Pedido();
        Integer id = 123;
        String numeroControle = "ABC123";
        LocalDate dataCadastro = LocalDate.now();
        String nomeProduto = "ProdutoTeste";
        double valorUnitario = 10.0;
        int quantidade = 5;
        Cliente cliente = new Cliente(); // Certifique-se de criar uma instância válida de Cliente
        double valorTotal = 50.0;

        pedido.setId(id);
        pedido.setNumeroControle(numeroControle);
        pedido.setDataCadastro(dataCadastro);
        pedido.setNomeProduto(nomeProduto);
        pedido.setValorUnitario(valorUnitario);
        pedido.setQuantidade(quantidade);
        pedido.setCliente(cliente);
        pedido.setValorTotal(valorTotal);

        assertEquals(id, pedido.getId());
        assertEquals(numeroControle, pedido.getNumeroControle());
        assertEquals(dataCadastro, pedido.getDataCadastro());
        assertEquals(nomeProduto, pedido.getNomeProduto());
        assertEquals(valorUnitario, pedido.getValorUnitario());
        assertEquals(quantidade, pedido.getQuantidade());
        assertEquals(cliente, pedido.getCliente());
        assertEquals(valorTotal, pedido.getValorTotal());
    }
}

