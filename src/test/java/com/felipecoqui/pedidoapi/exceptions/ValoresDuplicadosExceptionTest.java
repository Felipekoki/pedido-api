package com.felipecoqui.pedidoapi.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ValoresDuplicadosExceptionTest {

    @Test
    void getMessage_ShouldReturnCorrectMessage() {
        // Given
        String expectedMessage = "Valores duplicados encontrados";

        // When
        ValoresDuplicadosException exception = new ValoresDuplicadosException(expectedMessage);

        // Then
        assertEquals(expectedMessage, exception.getMessage());
    }
}

