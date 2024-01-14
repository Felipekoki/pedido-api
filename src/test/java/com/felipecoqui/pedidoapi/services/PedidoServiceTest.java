package com.felipecoqui.pedidoapi.services;

import com.felipecoqui.pedidoapi.dto.CadastroPedidoPayload;
import com.felipecoqui.pedidoapi.dto.FiltroPedidoPayload;
import com.felipecoqui.pedidoapi.dto.PedidoPayload;
import com.felipecoqui.pedidoapi.entities.Pedido;
import com.felipecoqui.pedidoapi.exceptions.NaoEncontradoException;
import com.felipecoqui.pedidoapi.exceptions.ValoresDuplicadosException;
import com.felipecoqui.pedidoapi.mappers.PedidoMapper;
import com.felipecoqui.pedidoapi.repositories.PedidoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PedidoServiceTest {

    @Autowired
    private PedidoService pedidoService;

    @MockBean
    private PedidoRepository pedidoRepository;

    @MockBean
    private PedidoMapper mapper;

    @Test
    public void testBuscarUm_NaoEncontrado() {
        Integer id = 1;

        when(pedidoRepository.findById(id)).thenReturn(java.util.Optional.empty());

        assertThrows(NaoEncontradoException.class, () -> pedidoService.buscarUm(id));
        verify(pedidoRepository, times(1)).findById(id);
    }

    @Test
    public void testFiltrarPedido_SemResultados() {
        FiltroPedidoPayload filtro = new FiltroPedidoPayload();
        PageRequest pageable = PageRequest.of(filtro.getPagina(), filtro.getTamanho());

        when(pedidoRepository.filtrarPedidos(any(), any(), any(), any())).thenReturn(Page.empty());

        Page<PedidoPayload> result = pedidoService.filtrarPedido(filtro);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(pedidoRepository, times(1)).filtrarPedidos(eq(filtro.getId()), eq(filtro.getNumeroControle()), eq(filtro.getDataCadastro()), eq(pageable));
    }

    @Test
    public void testCadstrar_Sucesso() throws NaoEncontradoException, ValoresDuplicadosException {
        List<CadastroPedidoPayload> cadastroPedidosPayload = criarListaDeCadastroPedidosPayload(new String[]{"ABC123", "ABC456"});
        List<Pedido> pedidos = criarListaPedidos(new String[]{"ABC123", "ABC456"});
        when(mapper.mapList(cadastroPedidosPayload)).thenReturn(pedidos);
        when(pedidoRepository.saveAll(pedidos)).thenReturn(pedidos);

        List<String> result = pedidoService.cadstrar(cadastroPedidosPayload);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(mapper, times(1)).mapList(cadastroPedidosPayload);
        verify(pedidoRepository, times(1)).saveAll(pedidos);
    }

    @Test
    public void testCadstrar_NumeroControleDuplicado() throws NaoEncontradoException, ValoresDuplicadosException {
        List<CadastroPedidoPayload> cadastroPedidosPayload = criarListaDeCadastroPedidosPayload(new String[]{"123","456","123"});
        when(pedidoRepository.findByNumeroControleIn(anySet())).thenReturn(Collections.singleton("123"));

        assertThrows(ValoresDuplicadosException.class, () -> pedidoService.cadstrar(cadastroPedidosPayload));
    }

    @Test
    public void testVerificarExistenciaDosNumerosDeControle_Duplicado() throws NaoEncontradoException, ValoresDuplicadosException {
        List<CadastroPedidoPayload> cadastroPedidosPayload = criarListaDeCadastroPedidosPayload(new String[]{"123","456","123"});
        when(pedidoRepository.findByNumeroControleIn(anySet())).thenReturn(Collections.singleton("123"));

        assertThrows(ValoresDuplicadosException.class, () -> pedidoService.verificarExistenciaDosNumerosDeControle(cadastroPedidosPayload));
    }

    @Test
    public void testVerificarExistenciaDosNumerosDeControle_SemDuplicados() throws NaoEncontradoException, ValoresDuplicadosException {
        List<CadastroPedidoPayload> cadastroPedidosPayload = criarListaDeCadastroPedidosPayload(new String[]{"123","456","789"});
        when(pedidoRepository.findByNumeroControleIn(anySet())).thenReturn(Collections.emptySet());

        assertDoesNotThrow(() -> pedidoService.verificarExistenciaDosNumerosDeControle(cadastroPedidosPayload));
        verify(pedidoRepository, times(1)).findByNumeroControleIn(anySet());
    }

    private List<CadastroPedidoPayload> criarListaDeCadastroPedidosPayload(String[] numerosControle) {
        return Arrays.stream(numerosControle)
                .map(numeroControle -> {
                    CadastroPedidoPayload cadastroPedidoPayload = new CadastroPedidoPayload();
                    cadastroPedidoPayload.setNumeroControle(numeroControle);
                    return cadastroPedidoPayload;
                })
                .collect(Collectors.toList());
    }

    private List<Pedido> criarListaPedidos(String[] numerosControle) {
        return Arrays.stream(numerosControle)
                .map(numeroControle -> {
                    Pedido pedido = new Pedido();
                    pedido.setNumeroControle(numeroControle);
                    return pedido;
                })
                .collect(Collectors.toList());
    }
}

