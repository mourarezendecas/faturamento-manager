package com.rezendev.faturamento_manager_api.controller;

import com.rezendev.faturamento_manager_api.model.dto.ClienteDTO;
import com.rezendev.faturamento_manager_api.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteDTO salvar(@RequestBody ClienteDTO clienteDTO) {
        return clienteService.criarCliente(clienteDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ClienteDTO listarClientePorId(@PathVariable Long id) {
        return clienteService.listarClientePorId(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ClienteDTO> listarClientes() {
        return clienteService.listarClientes();
    }

}
