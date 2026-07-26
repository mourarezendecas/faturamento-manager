package com.rezendev.faturamento_manager_api.service;

import com.rezendev.faturamento_manager_api.mapper.ItemMapper;
import com.rezendev.faturamento_manager_api.model.dto.ItemDTO;
import com.rezendev.faturamento_manager_api.model.entity.Item;
import com.rezendev.faturamento_manager_api.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public List<ItemDTO> listarItensPorPedido(Long pedidoId) {
        List<Item> itens = itemRepository.findByPedidoId(pedidoId);
        return itens.stream()
                .map(ItemMapper::entityToDTO)
                .toList();
    }

}
