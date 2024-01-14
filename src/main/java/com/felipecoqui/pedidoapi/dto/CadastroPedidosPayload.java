package com.felipecoqui.pedidoapi.dto;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.*;
import javax.xml.bind.annotation.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@XmlRootElement(name = "CadastroPedidosPayload")
@XmlAccessorType(XmlAccessType.FIELD)
public class CadastroPedidosPayload {

    @XmlElementWrapper(name = "pedidos")
    @XmlElement(name = "pedido")
    @NotNull(message = "Deve possuir uma lista de pedidos")
    @Size(min = 1, max = 10, message = "A lista de pedidos deve conter de 1 a 10 elementos")
    @Valid
    private List<@NotNull(message = "A lista de pedidos não pode conter elementos nulos") CadastroPedidoPayload> pedidos;
}
