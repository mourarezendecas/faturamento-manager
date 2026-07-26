package com.rezendev.faturamento_manager_api.controller;

import com.rezendev.faturamento_manager_api.model.dto.PedidoDTO;
import com.rezendev.faturamento_manager_api.model.entity.Pedido;
import com.rezendev.faturamento_manager_api.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<List<PedidoDTO>> listarTodos(
            @RequestParam(name = "idCliente", required = false) Long idCliente) {
        if (idCliente != null) {
            return ResponseEntity.ok(pedidoService.buscarPorCliente(idCliente));
        }
        return ResponseEntity.ok(pedidoService.listarTodos());
    }
}
