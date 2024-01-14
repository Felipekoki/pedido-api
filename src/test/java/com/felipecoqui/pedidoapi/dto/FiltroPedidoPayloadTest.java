package com.felipecoqui.pedidoapi.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class FiltroPedidoPayloadTest {

    @Test
    void testDefaultConstructor() {
        FiltroPedidoPayload filtro = new FiltroPedidoPayload();

        assertNull(filtro.getId());
        assertNull(filtro.getNumeroControle());
        assertNull(filtro.getDataCadastro());
        assertEquals(0, filtro.getPagina());
        assertEquals(10, filtro.getTamanho());
    }

    @Test
    void testParameterizedConstructor() {
        FiltroPedidoPayload filtro = new FiltroPedidoPayload(1, "ABC123", LocalDate.now(), 2, 20);

        assertEquals(1, filtro.getId());
        assertEquals("ABC123", filtro.getNumeroControle());
        assertEquals(LocalDate.now(), filtro.getDataCadastro());
        assertEquals(2, filtro.getPagina());
        assertEquals(20, filtro.getTamanho());
    }

    @Test
    void testSetterMethods() {
        FiltroPedidoPayload filtro = new FiltroPedidoPayload();

        filtro.setId(1);
        filtro.setNumeroControle("XYZ789");
        filtro.setDataCadastro(LocalDate.of(2022, 1, 1));
        filtro.setPagina(3);
        filtro.setTamanho(30);

        assertEquals(1, filtro.getId());
        assertEquals("XYZ789", filtro.getNumeroControle());
        assertEquals(LocalDate.of(2022, 1, 1), filtro.getDataCadastro());
        assertEquals(3, filtro.getPagina());
        assertEquals(30, filtro.getTamanho());
    }

}

