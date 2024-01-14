package com.felipecoqui.pedidoapi.resource;

import com.felipecoqui.pedidoapi.dto.CadastroPedidosPayload;
import com.felipecoqui.pedidoapi.dto.PedidoPayload;
import com.felipecoqui.pedidoapi.exceptions.NaoEncontradoException;
import com.felipecoqui.pedidoapi.exceptions.ValoresDuplicadosException;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.time.LocalDate;
import java.util.List;

@RequestMapping("/api/pedido")
public interface PedidoAPI {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<PedidoPayload>> buscarTodos();

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PedidoPayload> buscarUm(@PathParam("id") final Integer id) throws NaoEncontradoException;

    @GetMapping(value = "/filtro",produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Page<PedidoPayload>> filtrar(@RequestParam(required = false) Integer id,
                                                @RequestParam(required = false) String numeroControle,
                                                @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataCadastro,
                                                @RequestParam(defaultValue = "0") int pagina,
                                                @RequestParam(defaultValue = "10") int tamanho);

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Transactional
    ResponseEntity<List<String>> cadastrar(@Valid @RequestBody final CadastroPedidosPayload pedidos) throws NaoEncontradoException, ValoresDuplicadosException;
}
