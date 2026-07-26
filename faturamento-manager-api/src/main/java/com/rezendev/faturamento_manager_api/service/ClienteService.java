package com.rezendev.faturamento_manager_api.service;

import com.rezendev.faturamento_manager_api.exception.IdNotFoundException;
import com.rezendev.faturamento_manager_api.mapper.ClienteMapper;
import com.rezendev.faturamento_manager_api.model.dto.ClienteDTO;
import com.rezendev.faturamento_manager_api.model.entity.Cliente;
import com.rezendev.faturamento_manager_api.repository.ClienteRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository clienteRepository;

    @Transactional
    public ClienteDTO criarCliente(ClienteDTO clienteDTO) {

        Cliente cliente = ClienteMapper.DTOtoEntity(clienteDTO);
        Cliente clienteSalvo = clienteRepository.save(cliente);

        return ClienteMapper.entityToDTO(clienteSalvo);
    }

    @Transactional(readOnly = true)
    public ClienteDTO listarClientePorId(Long clienteId) {

        Cliente cliente = clienteRepository.findById(clienteId).orElseThrow(IdNotFoundException::new);

        return ClienteMapper.entityToDTO(cliente);
    }

    @Transactional(readOnly = true)
    public List<ClienteDTO> listarClientes(){
        List<Cliente> clientes = clienteRepository.findAll();
        return clientes.stream().map(ClienteMapper::entityToDTO).toList();
    }
}
