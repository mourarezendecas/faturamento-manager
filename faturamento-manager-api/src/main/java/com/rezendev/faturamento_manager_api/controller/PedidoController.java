package com.rezendev.faturamento_manager_api.controller;

import com.rezendev.faturamento_manager_api.exception.IdNotFoundException;
import com.rezendev.faturamento_manager_api.model.dto.PedidoDTO;
import com.rezendev.faturamento_manager_api.model.entity.Pedido;
import com.rezendev.faturamento_manager_api.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoDTO salvar(@RequestBody PedidoDTO pedidoDTO) {
        return pedidoService.criarPedido(pedidoDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PedidoDTO listarPedidoPorId(@PathVariable Long id){
        return pedidoService.listarPedidoPorId(id);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<PedidoDTO> listarPedidos() {
        return pedidoService.listarPedidos();
    }
}
