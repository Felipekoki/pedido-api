package com.felipecoqui.pedidoapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@AllArgsConstructor
@XmlRootElement(name = "pedido")
@XmlAccessorType(XmlAccessType.FIELD)
public class CadastroPedidoPayload extends PedidoBasePayload{

}
