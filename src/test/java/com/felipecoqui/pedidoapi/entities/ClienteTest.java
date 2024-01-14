package com.felipecoqui.pedidoapi.entities;

import com.felipecoqui.pedidoapi.repositories.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class ClienteTest {

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    void saveCliente_ShouldPersistClienteInDatabase() {
        // Given
        Cliente cliente = new Cliente();
        cliente.setNomeCliente("Nome do Cliente");

        // When
        Cliente savedCliente = clienteRepository.save(cliente);

        // Then
        assertNotNull(savedCliente.getId());
        assertEquals(cliente.getNomeCliente(), savedCliente.getNomeCliente());
    }

    @Test
    void findClienteById_ShouldReturnCorrectCliente() {
        // Given
        Cliente cliente = new Cliente();
        cliente.setNomeCliente("Nome do Cliente");
        Cliente savedCliente = clienteRepository.save(cliente);

        // When
        Optional<Cliente> foundCliente = clienteRepository.findById(savedCliente.getId());

        // Then
        assertTrue(foundCliente.isPresent());
        assertEquals(cliente.getNomeCliente(), foundCliente.get().getNomeCliente());
    }

    @Test
    void testSettersAndGetters() {
        // Given
        Cliente cliente = new Cliente(2);
        List<Pedido> pedidos = Arrays.asList(new Pedido());
        Integer idCliente = 123;
        String nomeCliente = "ClienteTeste";

        // When
        cliente.setId(idCliente);
        cliente.setNomeCliente(nomeCliente);
        cliente.setPedidos(pedidos);

        // Then
        assertEquals(idCliente, cliente.getId());
        assertEquals(nomeCliente, cliente.getNomeCliente());
        assertEquals(pedidos, cliente.getPedidos());
    }
}

