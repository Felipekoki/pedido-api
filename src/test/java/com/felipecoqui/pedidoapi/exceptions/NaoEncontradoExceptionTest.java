package com.felipecoqui.pedidoapi.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NaoEncontradoExceptionTest {

    @Test
    void getMessage_ShouldReturnCorrectMessage() {
        // Given
        String expectedMessage = "Objeto não encontrado";

        // When
        NaoEncontradoException exception = new NaoEncontradoException(expectedMessage);

        // Then
        assertEquals(expectedMessage, exception.getMessage());
    }
}

