package com.felipecoqui.pedidoapi.services;

import com.felipecoqui.pedidoapi.entities.Cliente;
import com.felipecoqui.pedidoapi.exceptions.NaoEncontradoException;
import com.felipecoqui.pedidoapi.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ClienteVerificationService {

    @Autowired
    private ClienteRepository clienteRepository;

    public void verificarExistenciaDosClientes(Set<Integer> codigoClientes) throws NaoEncontradoException {
        List<Cliente> clientesEncontrados = clienteRepository.findAllById(codigoClientes);
        if (clientesEncontrados.size() != codigoClientes.size()) {
            Set<Integer> clientesNaoEncontrados = new HashSet<>(codigoClientes);
            clientesNaoEncontrados.removeAll(clientesEncontrados.stream().map(Cliente::getId).collect(Collectors.toSet()));
            throw new NaoEncontradoException("Clientes não encontrados: " + clientesNaoEncontrados);
        }
    }


}
