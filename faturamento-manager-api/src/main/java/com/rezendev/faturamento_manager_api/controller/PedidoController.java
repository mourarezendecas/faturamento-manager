package com.rezendev.faturamento_manager_api.controller;

import com.rezendev.faturamento_manager_api.model.dto.AtualizarStatusDTO;
import com.rezendev.faturamento_manager_api.model.dto.ItemDTO;
import com.rezendev.faturamento_manager_api.model.dto.PedidoDTO;
import com.rezendev.faturamento_manager_api.model.enums.StatusEnum;
import com.rezendev.faturamento_manager_api.service.ItemService;
import com.rezendev.faturamento_manager_api.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;
    private final ItemService itemService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoDTO criarPedido(@RequestBody PedidoDTO pedidoDTO) {
        return pedidoService.criarPedido(pedidoDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PedidoDTO listarPedidoPorId(@PathVariable Long id){
        return pedidoService.listarPedidoPorId(id);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<PedidoDTO> listarPedidos(
            @RequestParam(required = false) StatusEnum status,
            @RequestParam(required = false) Long idCliente
    ) {
        return pedidoService.listarPedidos(status, idCliente);
    }

    @PostMapping("/{id}/itens")
    @ResponseStatus(HttpStatus.OK)
    public PedidoDTO adicionarItens(@PathVariable Long id, @RequestBody List<ItemDTO> itens) {
        return pedidoService.adicionarItens(id, itens);
    }

    @GetMapping("/{id}/itens")
    @ResponseStatus(HttpStatus.OK)
    public List<ItemDTO> listarItensPedido(@PathVariable Long id){
        return itemService.listarItensPorPedido(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarPedido(@PathVariable Long id){
        pedidoService.removePedido(id);
    }

    @PatchMapping("/{id}/status")
    @ResponseStatus(HttpStatus.OK)
    public PedidoDTO atualizarStatus(@PathVariable Long id, @RequestBody AtualizarStatusDTO statusDTO) {
        return pedidoService.atualizarStatus(id, statusDTO.getStatus());
    }
}
