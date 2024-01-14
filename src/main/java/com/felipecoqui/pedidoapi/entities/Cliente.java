package com.felipecoqui.pedidoapi.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "CLIENTE")
public class Cliente implements AbstractEntity<Integer>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CLIENTE")
    private Integer id;

    @Column(name = "NOME_CLIENTE", unique = true)
    protected String nomeCliente;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pedido> pedidos = new ArrayList<>();

    public Cliente(Integer id) {
        this.id = id;
    }
}
