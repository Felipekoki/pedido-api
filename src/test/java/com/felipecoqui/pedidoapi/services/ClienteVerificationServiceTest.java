package com.felipecoqui.pedidoapi.services;

import com.felipecoqui.pedidoapi.entities.Cliente;
import com.felipecoqui.pedidoapi.exceptions.NaoEncontradoException;
import com.felipecoqui.pedidoapi.repositories.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ClienteVerificationServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteVerificationService clienteVerificationService;

    private Cliente cliente1;
    private Cliente cliente2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        cliente1 = new Cliente();
        cliente1.setId(1);

        cliente2 = new Cliente();
        cliente2.setId(2);
    }

    @Test
    void verificarExistenciaDosClientes_TodosEncontrados() {
        Set<Integer> codigoClientes = new HashSet<>(Arrays.asList(1, 2));
        when(clienteRepository.findAllById(codigoClientes)).thenReturn(Arrays.asList(cliente1, cliente2));

        assertDoesNotThrow(() -> clienteVerificationService.verificarExistenciaDosClientes(codigoClientes));

        verify(clienteRepository, times(1)).findAllById(codigoClientes);
    }

    @Test
    void verificarExistenciaDosClientes_ClienteNaoEncontrado() {
        Set<Integer> codigoClientes = new HashSet<>(Arrays.asList(1, 2));
        when(clienteRepository.findAllById(codigoClientes)).thenReturn(Arrays.asList(cliente1));

        NaoEncontradoException exception = assertThrows(NaoEncontradoException.class,
                () -> clienteVerificationService.verificarExistenciaDosClientes(codigoClientes));

        verify(clienteRepository, times(1)).findAllById(codigoClientes);

        // Verifica a mensagem da exceção
        String expectedMessage = "Clientes não encontrados: [2]";
        String actualMessage = exception.getMessage();
        assert(expectedMessage.equals(actualMessage));
    }
}
