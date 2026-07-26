package com.rezendev.faturamento_manager_api.service;

import com.rezendev.faturamento_manager_api.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PedidoService {
    private final PedidoRepository pedidoRepository;

}
