package com.felipecoqui.pedidoapi.services;

import com.felipecoqui.pedidoapi.dto.CadastroPedidoPayload;
import com.felipecoqui.pedidoapi.dto.FiltroPedidoPayload;
import com.felipecoqui.pedidoapi.dto.PedidoPayload;
import com.felipecoqui.pedidoapi.entities.Pedido;
import com.felipecoqui.pedidoapi.exceptions.NaoEncontradoException;
import com.felipecoqui.pedidoapi.exceptions.ValoresDuplicadosException;
import com.felipecoqui.pedidoapi.mappers.PedidoMapper;
import com.felipecoqui.pedidoapi.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private PedidoMapper mapper;

    public List<PedidoPayload> buscarTodos(){
        List<Pedido> pedidos = pedidoRepository.findAll();
        return mapper.map(pedidos);
    }

    public PedidoPayload buscarUm(Integer id) throws NaoEncontradoException{
        Pedido pedido = obterPedido(id);
        return mapper.map(pedido);
    }

    public Page<PedidoPayload> filtrarPedido(FiltroPedidoPayload filtroPedidoPayload){
        Pageable pageable = PageRequest.of(filtroPedidoPayload.getPagina(), filtroPedidoPayload.getTamanho());
        return pedidoRepository.filtrarPedidos(filtroPedidoPayload.getId(), filtroPedidoPayload.getNumeroControle(), filtroPedidoPayload.getDataCadastro(), pageable).map(mapper::map);
    }

    @Transactional
    public List<String> cadstrar(List<CadastroPedidoPayload> cadastroPedidosPayload) throws NaoEncontradoException, ValoresDuplicadosException {
        verificarExistenciaDosNumerosDeControle(cadastroPedidosPayload);
        List<Pedido> pedidos = mapper.mapList(cadastroPedidosPayload);
        pedidoRepository.saveAll(pedidos);
        return pedidos.stream().map(Pedido::getNumeroControle).collect(Collectors.toList());
    }

    private Pedido obterPedido(Integer id) throws NaoEncontradoException{
        return pedidoRepository.findById(id).orElseThrow(() -> new NaoEncontradoException("Pedido não encontrado"));
    }

    public void verificarExistenciaDosNumerosDeControle(List<CadastroPedidoPayload> cadastroPedidosPayload) throws NaoEncontradoException, ValoresDuplicadosException {
        verificaDuplicados(cadastroPedidosPayload);
        Set<String> numerosControle = cadastroPedidosPayload.stream()
                .map(CadastroPedidoPayload::getNumeroControle)
                .collect(Collectors.toSet());
        Set<String> numerosControleExistente = pedidoRepository.findByNumeroControleIn(numerosControle);
        if (!numerosControleExistente.isEmpty()) {
            throw new ValoresDuplicadosException("Número de controle duplicado encontrado: " + numerosControleExistente);
        }
    }

    private void verificaDuplicados(List<CadastroPedidoPayload> cadastroPedidosPayload) throws ValoresDuplicadosException {
        Set<String> numerosControleSet = new HashSet<>();
        if (cadastroPedidosPayload.stream().anyMatch(p -> !numerosControleSet.add(p.getNumeroControle()))) {
            throw new ValoresDuplicadosException("Número de controle duplicado encontrado na lista.");
        }
    }
}
