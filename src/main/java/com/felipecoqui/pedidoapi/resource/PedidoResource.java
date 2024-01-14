package com.felipecoqui.pedidoapi.resource;

import com.felipecoqui.pedidoapi.dto.CadastroPedidoPayload;
import com.felipecoqui.pedidoapi.dto.CadastroPedidosPayload;
import com.felipecoqui.pedidoapi.dto.FiltroPedidoPayload;
import com.felipecoqui.pedidoapi.dto.PedidoPayload;
import com.felipecoqui.pedidoapi.exceptions.NaoEncontradoException;
import com.felipecoqui.pedidoapi.exceptions.ValoresDuplicadosException;
import com.felipecoqui.pedidoapi.services.ClienteVerificationService;
import com.felipecoqui.pedidoapi.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class PedidoResource implements PedidoAPI {

    @Autowired
    private PedidoService pedidoService;
    @Autowired
    private ClienteVerificationService clienteVerificationService;

    @Override
    public ResponseEntity<List<PedidoPayload>> buscarTodos() {
        return ApiResponse.ok(pedidoService.buscarTodos());
    }

    @Override
    public ResponseEntity<PedidoPayload> buscarUm(Integer id) throws NaoEncontradoException {
        return ApiResponse.ok(pedidoService.buscarUm(id));
    }

    @Override
    public ResponseEntity<Page<PedidoPayload>> filtrar(Integer id, String numeroControle, LocalDate dataCadastro, int pagina, int tamanho) {
        return ApiResponse.ok(pedidoService.filtrarPedido(new FiltroPedidoPayload(id, numeroControle, dataCadastro, pagina, tamanho)));
    }

    @Override
    public ResponseEntity<List<String>> cadastrar(CadastroPedidosPayload cadastroPedidosPayload) throws NaoEncontradoException, ValoresDuplicadosException {
        Set<Integer> codigoClientes = cadastroPedidosPayload.getPedidos().stream()
                .map(CadastroPedidoPayload::getCodigoCliente)
                .collect(Collectors.toSet());
        clienteVerificationService.verificarExistenciaDosClientes(codigoClientes);
        return ApiResponse.ok(pedidoService.cadstrar(cadastroPedidosPayload.getPedidos()));
    }
}
