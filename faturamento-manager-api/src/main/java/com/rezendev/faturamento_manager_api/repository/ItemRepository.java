package com.rezendev.faturamento_manager_api.repository;

import com.rezendev.faturamento_manager_api.model.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByPedidoId(Long pedidoId);
}
