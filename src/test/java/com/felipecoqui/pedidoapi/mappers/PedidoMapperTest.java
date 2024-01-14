package com.felipecoqui.pedidoapi.mappers;

import com.felipecoqui.pedidoapi.dto.CadastroPedidoPayload;
import com.felipecoqui.pedidoapi.dto.PedidoPayload;
import com.felipecoqui.pedidoapi.entities.Pedido;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class PedidoMapperTest {

    private final PedidoMapper pedidoMapper = new PedidoMapperImpl();

    @Test
    void mapList() {
        CadastroPedidoPayload pedidoPayload1 = new CadastroPedidoPayload();
        CadastroPedidoPayload pedidoPayload2 = new CadastroPedidoPayload();
        List<CadastroPedidoPayload> cadastroPedidoPayloads = Arrays.asList(pedidoPayload1, pedidoPayload2);
        List<Pedido> pedidos = pedidoMapper.mapList(cadastroPedidoPayloads);
        assertNotNull(pedidos);
        assertEquals(cadastroPedidoPayloads.size(), pedidos.size());
    }

    @Test
    void mapList_withNullInput() {
        List<Pedido> pedidos = pedidoMapper.mapList(null);
        assertNull(pedidos);
    }

    @Test
    void map() {
        Pedido pedido1 = new Pedido();
        Pedido pedido2 = new Pedido();
        List<Pedido> pedidos = Arrays.asList(pedido1, pedido2);

        List<PedidoPayload> pedidoPayloads = pedidoMapper.map(pedidos);
        assertNotNull(pedidoPayloads);
        assertEquals(pedidos.size(), pedidoPayloads.size());
    }

    @Test
    void mapPedido() {
        Pedido pedido = new Pedido();
        PedidoPayload pedidoPayload = pedidoMapper.map(pedido);
        assertNotNull(pedidoPayload);
    }
}

