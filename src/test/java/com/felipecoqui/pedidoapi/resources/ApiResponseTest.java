package com.felipecoqui.pedidoapi.resources;

import com.felipecoqui.pedidoapi.resource.ApiResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ApiResponseTest {

    @Test
    void ok_ShouldReturnOkResponseWithEntity() {
        // Given
        String expectedEntity = "Hello, World!";

        // When
        ResponseEntity<String> responseEntity = ApiResponse.ok(expectedEntity);

        // Then
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(expectedEntity, responseEntity.getBody());
    }
}

