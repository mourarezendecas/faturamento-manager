package com.rezendev.faturamento_manager_api.service;

import com.rezendev.faturamento_manager_api.exception.IdNotFoundException;
import com.rezendev.faturamento_manager_api.mapper.ClienteMapper;
import com.rezendev.faturamento_manager_api.mapper.PedidoMapper;
import com.rezendev.faturamento_manager_api.model.dto.PedidoDTO;
import com.rezendev.faturamento_manager_api.model.entity.Cliente;
import com.rezendev.faturamento_manager_api.model.entity.Pedido;
import com.rezendev.faturamento_manager_api.repository.ClienteRepository;
import com.rezendev.faturamento_manager_api.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;

    @Transactional
    public PedidoDTO criarPedido(@RequestBody PedidoDTO pedidoDTO) {

        Cliente cliente = clienteRepository.findById(pedidoDTO.getClienteID()).orElseThrow(IdNotFoundException::new);

        Pedido pedido = PedidoMapper.DTOToEntity(pedidoDTO, cliente);
        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        return PedidoMapper.entityToDTO(pedidoSalvo);
    }

    @Transactional(readOnly = true)
    public PedidoDTO listarPedidoPorId(Long pedidoId) throws IdNotFoundException {
        Pedido pedido =  pedidoRepository.findById(pedidoId).orElseThrow(IdNotFoundException::new);
        return PedidoMapper.entityToDTO(pedido);
    }

    @Transactional(readOnly = true)
    public List<PedidoDTO> listarPedidos() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        return pedidos.stream().map(PedidoMapper::entityToDTO).toList();
    }
}
