package com.felipecoqui.pedidoapi.dto;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PedidoPayloadTest {
    @Test
    public void testConstrutorComParametros() {
        Integer id = 1;
        double valorTotal = 100.0;
        PedidoPayload pedidoPayload = new PedidoPayload(id, valorTotal);
        assertNotNull(pedidoPayload);
        assertEquals(id, pedidoPayload.getId());
        assertEquals(valorTotal, pedidoPayload.getValorTotal(), 0.001);
    }

    @Test
    public void testConstrutorSemParametros() {
        PedidoPayload pedidoPayload = new PedidoPayload();
        assertNotNull(pedidoPayload);
        assertNull(pedidoPayload.getId());
        assertEquals(0.0, pedidoPayload.getValorTotal(), 0.001);
    }

    @Test
    public void testGetSetId() {
        Integer id = 1;
        PedidoPayload pedidoPayload = new PedidoPayload();
        pedidoPayload.setId(id);
        assertEquals(id, pedidoPayload.getId());
    }

    @Test
    public void testGetSetValorTotal() {
        double valorTotal = 100.0;
        PedidoPayload pedidoPayload = new PedidoPayload();
        pedidoPayload.setValorTotal(valorTotal);
        assertEquals(valorTotal, pedidoPayload.getValorTotal(), 0.001);
    }
}
