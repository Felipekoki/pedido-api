package com.felipecoqui.pedidoapi.exceptions;

import com.felipecoqui.pedidoapi.resource.PedidoResource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void handleMethodArgumentNotValid_ShouldReturnBadRequestWithErrors() throws Exception {
        String jsonPayload = "{}"; // Seu payload JSON inválido aqui

        mockMvc.perform(MockMvcRequestBuilders.post("/api/pedido")
                        .content(jsonPayload)
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").exists());
    }
}

