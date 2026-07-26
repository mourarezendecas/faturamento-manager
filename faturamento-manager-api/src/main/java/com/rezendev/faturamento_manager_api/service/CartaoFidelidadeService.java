package com.rezendev.faturamento_manager_api.service;

import com.rezendev.faturamento_manager_api.exception.IdNotFoundException;
import com.rezendev.faturamento_manager_api.mapper.CartaoFidelidadeMapper;
import com.rezendev.faturamento_manager_api.mapper.ClienteMapper;
import com.rezendev.faturamento_manager_api.model.dto.CartaoFidelidadeDTO;
import com.rezendev.faturamento_manager_api.model.entity.CartaoFidelidade;
import com.rezendev.faturamento_manager_api.repository.CartaoFidelidadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartaoFidelidadeService {
    private final CartaoFidelidadeRepository cartaoFidelidadeRepository;

    @Transactional
    public CartaoFidelidadeDTO criarCartaoFidelidade(CartaoFidelidadeDTO cartaoFidelidadeDTO) {

        CartaoFidelidade cartaoFidelidade = CartaoFidelidadeMapper.DTOToEntity(cartaoFidelidadeDTO);
        CartaoFidelidade cartaoFidelidadeSalvo = cartaoFidelidadeRepository.save(cartaoFidelidade);

        return CartaoFidelidadeMapper.entityToDTO(cartaoFidelidadeSalvo);
    }

    @Transactional(readOnly = true)
    public CartaoFidelidadeDTO buscarCartaoFidelidadePorId(Long id) {

        CartaoFidelidade cartaoFidelidade = cartaoFidelidadeRepository.findById(id).orElseThrow(IdNotFoundException::new);

        return CartaoFidelidadeMapper.entityToDTO(cartaoFidelidade);
    }

    @Transactional(readOnly = true)
    public List<CartaoFidelidadeDTO> listarCartoesFidelidade(){

        List<CartaoFidelidade> cartoesFidelidade = cartaoFidelidadeRepository.findAll();

        return cartoesFidelidade.stream().map(CartaoFidelidadeMapper::entityToDTO).toList();
    }

}
