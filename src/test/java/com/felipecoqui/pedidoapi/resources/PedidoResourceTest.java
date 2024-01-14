package com.felipecoqui.pedidoapi.resources;

import com.felipecoqui.pedidoapi.dto.CadastroPedidoPayload;
import com.felipecoqui.pedidoapi.dto.CadastroPedidosPayload;
import com.felipecoqui.pedidoapi.dto.FiltroPedidoPayload;
import com.felipecoqui.pedidoapi.dto.PedidoPayload;
import com.felipecoqui.pedidoapi.exceptions.NaoEncontradoException;
import com.felipecoqui.pedidoapi.exceptions.ValoresDuplicadosException;
import com.felipecoqui.pedidoapi.resource.PedidoResource;
import com.felipecoqui.pedidoapi.services.ClienteVerificationService;
import com.felipecoqui.pedidoapi.services.PedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PedidoResourceTest {

    @Mock
    private PedidoService pedidoService;

    @Mock
    private ClienteVerificationService clienteVerificationService;

    @InjectMocks
    private PedidoResource pedidoResource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void buscarTodos_DeveRetornarPedidos() {
        List<PedidoPayload> pedidos = Collections.singletonList(new PedidoPayload());
        when(pedidoService.buscarTodos()).thenReturn(pedidos);

        ResponseEntity<List<PedidoPayload>> response = pedidoResource.buscarTodos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pedidos, response.getBody());
    }

    @Test
    void buscarUm_DeveRetornarPedido() throws NaoEncontradoException {
        Integer pedidoId = 1;
        PedidoPayload pedido = new PedidoPayload();
        when(pedidoService.buscarUm(pedidoId)).thenReturn(pedido);

        ResponseEntity<PedidoPayload> response = pedidoResource.buscarUm(pedidoId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pedido, response.getBody());
    }

    @Test
    void buscarUm_PedidoNaoEncontrado_DeveRetornarNotFound() throws NaoEncontradoException {
        Integer pedidoId = 1;
        when(pedidoService.buscarUm(pedidoId)).thenThrow(new NaoEncontradoException("Pedido não encontrado"));

        assertThrows(NaoEncontradoException.class, () -> pedidoResource.buscarUm(pedidoId));
    }

    @Test
    void filtrar_DeveRetornarPedidosFiltrados() {
        FiltroPedidoPayload filtro = new FiltroPedidoPayload();
        List<PedidoPayload> pedidosFiltrados = Collections.emptyList();
        Page<PedidoPayload> page = new PageImpl<>(pedidosFiltrados, PageRequest.of(0, 10), pedidosFiltrados.size());

        when(pedidoService.filtrarPedido(any(FiltroPedidoPayload.class))).thenReturn(page);

        ResponseEntity<Page<PedidoPayload>> response = pedidoResource.filtrar(
                filtro.getId(), filtro.getNumeroControle(), filtro.getDataCadastro(), 1, 10);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(page, response.getBody());

    }

    @Test
    void cadastrar_DeveCadastrarPedidos() throws NaoEncontradoException, ValoresDuplicadosException {
        CadastroPedidosPayload cadastroPedidosPayload = new CadastroPedidosPayload(Arrays.asList(new CadastroPedidoPayload()));
        Set<Integer> codigoClientes = Collections.singleton(1);
        List<String> codigoPedidos = Arrays.asList("ABC123", "DEF123");

        doNothing().when(clienteVerificationService).verificarExistenciaDosClientes(codigoClientes);
        when(pedidoService.cadstrar(cadastroPedidosPayload.getPedidos())).thenReturn(codigoPedidos);

        ResponseEntity<List<String>> response = pedidoResource.cadastrar(cadastroPedidosPayload);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(codigoPedidos, response.getBody());
    }

    @Test
    void cadastrar_ClienteNaoEncontrado_DeveRetornarBadRequest() throws NaoEncontradoException, ValoresDuplicadosException {
        CadastroPedidosPayload cadastroPedidosPayload = new CadastroPedidosPayload(Arrays.asList(new CadastroPedidoPayload()));

        doThrow(new NaoEncontradoException("Cliente não encontrado")).when(clienteVerificationService)
                .verificarExistenciaDosClientes(anySet());

        try {
            ResponseEntity<List<String>> response = pedidoResource.cadastrar(cadastroPedidosPayload);
            fail("NaoEncontradoException não foi lançada");
        } catch (NaoEncontradoException e) {
            assertEquals("Cliente não encontrado", e.getMessage());
        }

        verify(pedidoService, never()).cadstrar(anyList());
    }
}

