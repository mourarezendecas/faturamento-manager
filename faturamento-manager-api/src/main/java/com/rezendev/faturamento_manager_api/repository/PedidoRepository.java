package com.rezendev.faturamento_manager_api.repository;

import com.rezendev.faturamento_manager_api.model.entity.Pedido;
import com.rezendev.faturamento_manager_api.model.enums.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByClienteId(Long idCliente);

    @Query("""
        SELECT p FROM Pedido p
        WHERE (:status IS NULL OR p.status = :status)
        AND (:idCliente IS NULL OR p.cliente.id = :idCliente)
        """)
    List<Pedido> findByFiltros(@Param("status") StatusEnum status,
                               @Param("idCliente") Long idCliente);
}