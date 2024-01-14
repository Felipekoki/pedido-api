package com.felipecoqui.pedidoapi.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "PEDIDO")
public class Pedido implements AbstractEntity<Integer>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PEDIDO")
    private Integer id;
    @Column(name = "NUMERO_CONTROLE", unique = true)
    private String numeroControle;
    @Column(name = "DATA_CADASTRO")
    private LocalDate dataCadastro;
    @Column(name = "NOME_PRODUTO")
    private String nomeProduto;
    @Column(name = "VALOR_UNITARIO")
    private double valorUnitario;
    @Column(name = "QUANTIDADE")
    private int quantidade;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CLIENTE")
    private Cliente cliente;
    @Column(name = "VALOR_TOTAL")
    private double valorTotal;

    public Pedido(String numeroControle, LocalDate dataCadastro, String nomeProduto, double valorUnitario, int quantidade, Cliente cliente) {
        this.numeroControle = numeroControle;
        this.dataCadastro = dataCadastro;
        this.nomeProduto = nomeProduto;
        this.valorUnitario = valorUnitario;
        this.quantidade = quantidade;
        this.cliente = cliente;
    }

    public void calcularValorTotal(){
        valorTotal = quantidade * valorUnitario;
        calcularDesconto();
    }

    private void calcularDesconto(){
        if(quantidade > 4){
            double desconto = (quantidade < 10) ? 5 : 10;
            valorTotal -= valorTotal * (desconto / 100.0);
        }
    }
}
