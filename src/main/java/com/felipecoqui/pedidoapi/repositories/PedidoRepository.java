package com.felipecoqui.pedidoapi.repositories;

import com.felipecoqui.pedidoapi.entities.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Set;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    @Query("SELECT p FROM PEDIDO p WHERE " +
            "(:id IS NULL OR p.id = :id) AND " +
            "(:numeroControle IS NULL OR p.numeroControle = :numeroControle) AND " +
            "(:dataCadastro IS NULL OR p.dataCadastro = :dataCadastro)")
    Page<Pedido> filtrarPedidos(@Param("numeroControle") Integer id, @Param("numeroControle") String numeroControle, @Param("dataCadastro") LocalDate dataCadastro, Pageable pageable);

    @Query("SELECT DISTINCT p.numeroControle FROM PEDIDO p WHERE p.numeroControle IN :numerosControle")
    Set<String> findByNumeroControleIn(@Param("numerosControle") Set<String> numerosControle);
}
