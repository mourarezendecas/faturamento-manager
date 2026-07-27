package com.rezendev.faturamento_manager_api.service;

import com.rezendev.faturamento_manager_api.exception.IdNotFoundException;
import com.rezendev.faturamento_manager_api.mapper.ItemMapper;
import com.rezendev.faturamento_manager_api.mapper.PedidoMapper;
import com.rezendev.faturamento_manager_api.model.dto.ItemDTO;
import com.rezendev.faturamento_manager_api.model.dto.PedidoDTO;
import com.rezendev.faturamento_manager_api.model.entity.Cliente;
import com.rezendev.faturamento_manager_api.model.entity.Item;
import com.rezendev.faturamento_manager_api.model.entity.Pedido;
import com.rezendev.faturamento_manager_api.model.enums.StatusEnum;
import com.rezendev.faturamento_manager_api.repository.ClienteRepository;
import com.rezendev.faturamento_manager_api.repository.PedidoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private PedidoService pedidoService;

    private MockedStatic<PedidoMapper> pedidoMapperMock;
    private MockedStatic<ItemMapper> itemMapperMock;

    @BeforeEach
    void setUp() {
        pedidoMapperMock = mockStatic(PedidoMapper.class);
        itemMapperMock = mockStatic(ItemMapper.class);
    }

    @AfterEach
    void tearDown() {
        pedidoMapperMock.close();
        itemMapperMock.close();
    }

    // ---------- criarPedido ----------

    @Test
    void deveCriarPedidoComSucesso() {
        Long clienteId = 1L;
        PedidoDTO pedidoDTORequisicao = mock(PedidoDTO.class);
        Cliente cliente = mock(Cliente.class);
        Pedido pedidoParaSalvar = mock(Pedido.class);
        Pedido pedidoSalvo = mock(Pedido.class);
        PedidoDTO pedidoDTOResposta = mock(PedidoDTO.class);

        when(pedidoDTORequisicao.getClienteID()).thenReturn(clienteId);
        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));
        pedidoMapperMock.when(() -> PedidoMapper.DTOToEntity(pedidoDTORequisicao, cliente))
                .thenReturn(pedidoParaSalvar);
        when(pedidoRepository.save(pedidoParaSalvar)).thenReturn(pedidoSalvo);
        pedidoMapperMock.when(() -> PedidoMapper.entityToDTO(pedidoSalvo))
                .thenReturn(pedidoDTOResposta);

        PedidoDTO resultado = pedidoService.criarPedido(pedidoDTORequisicao);

        assertThat(resultado).isEqualTo(pedidoDTOResposta);
        verify(clienteRepository).findById(clienteId);
        verify(pedidoRepository).save(pedidoParaSalvar);
    }

    @Test
    void deveLancarExcecaoAoCriarPedidoComClienteInexistente() {
        Long clienteId = 99L;
        PedidoDTO pedidoDTORequisicao = mock(PedidoDTO.class);
        when(pedidoDTORequisicao.getClienteID()).thenReturn(clienteId);
        when(clienteRepository.findById(clienteId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> pedidoService.criarPedido(pedidoDTORequisicao))
                .isInstanceOf(IdNotFoundException.class);

        verify(pedidoRepository, never()).save(any());
    }

    // ---------- listarPedidoPorId ----------

    @Test
    void deveListarPedidoPorIdComSucesso() {
        Long pedidoId = 1L;
        Pedido pedido = mock(Pedido.class);
        PedidoDTO pedidoDTO = mock(PedidoDTO.class);

        when(pedidoRepository.findById(pedidoId)).thenReturn(Optional.of(pedido));
        pedidoMapperMock.when(() -> PedidoMapper.entityToDTO(pedido)).thenReturn(pedidoDTO);

        PedidoDTO resultado = pedidoService.listarPedidoPorId(pedidoId);

        assertThat(resultado).isEqualTo(pedidoDTO);
    }

    @Test
    void deveLancarExcecaoAoListarPedidoPorIdInexistente() {
        Long pedidoId = 404L;
        when(pedidoRepository.findById(pedidoId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> pedidoService.listarPedidoPorId(pedidoId))
                .isInstanceOf(IdNotFoundException.class);
    }

    // ---------- listarPedidos ----------

    @Test
    void deveListarPedidosComFiltros() {
        StatusEnum status = StatusEnum.FATURADO; // ajuste para um valor válido do seu enum
        Long idCliente = 1L;

        Pedido pedido1 = mock(Pedido.class);
        Pedido pedido2 = mock(Pedido.class);
        PedidoDTO dto1 = mock(PedidoDTO.class);
        PedidoDTO dto2 = mock(PedidoDTO.class);

        when(pedidoRepository.findByFiltros(status, idCliente))
                .thenReturn(List.of(pedido1, pedido2));
        pedidoMapperMock.when(() -> PedidoMapper.entityToDTO(pedido1)).thenReturn(dto1);
        pedidoMapperMock.when(() -> PedidoMapper.entityToDTO(pedido2)).thenReturn(dto2);

        List<PedidoDTO> resultado = pedidoService.listarPedidos(status, idCliente);

        assertThat(resultado).containsExactly(dto1, dto2);
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHouverPedidos() {
        when(pedidoRepository.findByFiltros(null, null)).thenReturn(List.of());

        List<PedidoDTO> resultado = pedidoService.listarPedidos(null, null);

        assertThat(resultado).isEmpty();
    }

    // ---------- adicionarItens ----------

    @Test
    void deveAdicionarItensComSucesso() {
        Long pedidoId = 1L;
        ItemDTO itemDTO = mock(ItemDTO.class);
        Item item = mock(Item.class);
        Pedido pedido = new Pedido();
        pedido.setItens(new ArrayList<>());
        Pedido pedidoAtualizado = mock(Pedido.class);
        PedidoDTO pedidoDTOResposta = mock(PedidoDTO.class);

        when(pedidoRepository.findById(pedidoId)).thenReturn(Optional.of(pedido));
        itemMapperMock.when(() -> ItemMapper.dtoToEntity(itemDTO)).thenReturn(item);
        when(pedidoRepository.save(pedido)).thenReturn(pedidoAtualizado);
        pedidoMapperMock.when(() -> PedidoMapper.entityToDTO(pedidoAtualizado)).thenReturn(pedidoDTOResposta);

        PedidoDTO resultado = pedidoService.adicionarItens(pedidoId, List.of(itemDTO));

        assertThat(resultado).isEqualTo(pedidoDTOResposta);
        assertThat(pedido.getItens()).contains(item);
        verify(item).setPedido(pedido);
        verify(pedidoRepository).save(pedido);
    }

    @Test
    void deveLancarExcecaoAoAdicionarItensEmPedidoInexistente() {
        Long pedidoId = 500L;
        when(pedidoRepository.findById(pedidoId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> pedidoService.adicionarItens(pedidoId, List.of(mock(ItemDTO.class))))
                .isInstanceOf(IdNotFoundException.class);

        verify(pedidoRepository, never()).save(any());
    }

    // ---------- removePedido ----------

    @Test
    void deveRemoverPedidoComSucesso() {
        Long pedidoId = 1L;
        Pedido pedido = mock(Pedido.class);
        when(pedidoRepository.findById(pedidoId)).thenReturn(Optional.of(pedido));

        pedidoService.removePedido(pedidoId);

        verify(pedidoRepository).deleteById(pedidoId);
    }

    @Test
    void deveLancarExcecaoAoRemoverPedidoInexistente() {
        Long pedidoId = 999L;
        when(pedidoRepository.findById(pedidoId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> pedidoService.removePedido(pedidoId))
                .isInstanceOf(IdNotFoundException.class);

        verify(pedidoRepository, never()).deleteById(anyLong());
    }

    // ---------- atualizarStatus ----------

    @Test
    void deveAtualizarStatusComSucesso() {
        Long pedidoId = 1L;
        StatusEnum novoStatus = StatusEnum.CANCELADO; // ajuste para um valor válido do seu enum
        Pedido pedido = mock(Pedido.class);
        Pedido pedidoAtualizado = mock(Pedido.class);
        PedidoDTO pedidoDTOResposta = mock(PedidoDTO.class);

        when(pedidoRepository.findById(pedidoId)).thenReturn(Optional.of(pedido));
        when(pedidoRepository.save(pedido)).thenReturn(pedidoAtualizado);
        pedidoMapperMock.when(() -> PedidoMapper.entityToDTO(pedidoAtualizado)).thenReturn(pedidoDTOResposta);

        PedidoDTO resultado = pedidoService.atualizarStatus(pedidoId, novoStatus);

        assertThat(resultado).isEqualTo(pedidoDTOResposta);
        verify(pedido).setStatus(novoStatus);
        verify(pedidoRepository).save(pedido);
    }

    @Test
    void deveLancarExcecaoAoAtualizarStatusDePedidoInexistente() {
        Long pedidoId = 42L;
        when(pedidoRepository.findById(pedidoId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> pedidoService.atualizarStatus(pedidoId, StatusEnum.CANCELADO))
                .isInstanceOf(IdNotFoundException.class);

        verify(pedidoRepository, never()).save(any());
    }
}