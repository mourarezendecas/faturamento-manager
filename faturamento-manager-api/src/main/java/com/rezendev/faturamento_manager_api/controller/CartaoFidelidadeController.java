package com.rezendev.faturamento_manager_api.controller;

import com.rezendev.faturamento_manager_api.model.dto.CartaoFidelidadeDTO;
import com.rezendev.faturamento_manager_api.service.CartaoFidelidadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cartoes-fidelidade")
@RequiredArgsConstructor
public class CartaoFidelidadeController {
    private final CartaoFidelidadeService cartaoFidelidadeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CartaoFidelidadeDTO salvar(@RequestBody CartaoFidelidadeDTO cartaoDTO) {
        return cartaoFidelidadeService.criarCartaoFidelidade(cartaoDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CartaoFidelidadeDTO buscarCartaoFidelidadePorId(@PathVariable Long id) {
        return cartaoFidelidadeService.buscarCartaoFidelidadePorId(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CartaoFidelidadeDTO> listarCartoesFidelidade() {
        return cartaoFidelidadeService.listarCartoesFidelidade();
    }
}
