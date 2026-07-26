package com.rezendev.faturamento_manager_api.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 255)
    private String nome;

    @Column(nullable = false, length = 255)
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    private CartaoFidelidade cartaoFidelidade;

    @OneToMany(mappedBy = "cliente",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Pedido> pedidos = new ArrayList<>();
}
