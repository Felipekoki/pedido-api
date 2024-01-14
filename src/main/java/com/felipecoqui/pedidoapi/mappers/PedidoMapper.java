package com.felipecoqui.pedidoapi.mappers;

import com.felipecoqui.pedidoapi.dto.CadastroPedidoPayload;
import com.felipecoqui.pedidoapi.dto.PedidoPayload;
import com.felipecoqui.pedidoapi.entities.Cliente;
import com.felipecoqui.pedidoapi.entities.Pedido;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface PedidoMapper {

    default Pedido map(CadastroPedidoPayload cadastroPedidoPayload){
        Pedido pedido = new Pedido(
                cadastroPedidoPayload.getNumeroControle(),
                cadastroPedidoPayload.getDataCadastro(),
                cadastroPedidoPayload.getNomeProduto(),
                cadastroPedidoPayload.getValorUnitario(),
                cadastroPedidoPayload.getQuantidade(),
                new Cliente(cadastroPedidoPayload.getCodigoCliente())
        );
        pedido.calcularValorTotal();
        return pedido;
    }

    List<Pedido> mapList(List<CadastroPedidoPayload> cadastroPedidoPayloads);

    List<PedidoPayload> map(List<Pedido> pedidos);

    PedidoPayload map(Pedido pedido);
}
