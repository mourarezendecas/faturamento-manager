package com.rezendev.faturamento_manager_api.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CartaoFidelidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer pontos;

    @OneToOne
    @JoinColumn(
            name = "id_cliente",
            nullable = false,
            unique = true
    )
    private Cliente cliente;
}
