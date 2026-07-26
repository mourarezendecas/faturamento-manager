package com.rezendev.faturamento_manager_api.repository;

import com.rezendev.faturamento_manager_api.model.entity.CartaoFidelidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartaoFidelidadeRepository extends JpaRepository<CartaoFidelidade, Long> {
}
