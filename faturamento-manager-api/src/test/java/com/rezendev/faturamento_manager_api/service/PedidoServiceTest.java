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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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
        LocalDateTime dataPedido = LocalDateTime.now();

        PedidoDTO pedidoDTORequisicao = PedidoDTO.builder()
                .dataPedido(dataPedido)
                .clienteID(clienteId)
                .build();

        Cliente cliente = Cliente.builder()
                .id(clienteId)
                .nome("Fulano")
                .email("fulano@email.com")
                .build();

        Pedido pedidoParaSalvar = Pedido.builder()
                .dataPedido(dataPedido)
                .cliente(cliente)
                .build();

        Pedido pedidoSalvo = Pedido.builder()
                .id(10L)
                .dataPedido(dataPedido)
                .cliente(cliente)
                .status(StatusEnum.CRIADO)
                .build();

        PedidoDTO pedidoDTOResposta = PedidoDTO.builder()
                .id(10L)
                .dataPedido(dataPedido)
                .clienteID(clienteId)
                .status(StatusEnum.CRIADO)
                .build();

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
        PedidoDTO pedidoDTORequisicao = PedidoDTO.builder()
                .dataPedido(LocalDateTime.now())
                .clienteID(clienteId)
                .build();

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> pedidoService.criarPedido(pedidoDTORequisicao))
                .isInstanceOf(IdNotFoundException.class);

        verify(pedidoRepository, never()).save(any());
    }

    // ---------- listarPedidoPorId ----------

    @Test
    void deveListarPedidoPorIdComSucesso() {
        Long pedidoId = 1L;
        Pedido pedido = Pedido.builder().id(pedidoId).status(StatusEnum.CRIADO).build();
        PedidoDTO pedidoDTO = PedidoDTO.builder().id(pedidoId).status(StatusEnum.CRIADO).build();

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
        StatusEnum status = StatusEnum.CRIADO;
        Long idCliente = 1L;

        Pedido pedido1 = Pedido.builder().id(1L).status(status).build();
        Pedido pedido2 = Pedido.builder().id(2L).status(status).build();
        PedidoDTO dto1 = PedidoDTO.builder().id(1L).status(status).build();
        PedidoDTO dto2 = PedidoDTO.builder().id(2L).status(status).build();

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

        Pedido pedido = Pedido.builder()
                .id(pedidoId)
                .status(StatusEnum.CRIADO)
                .build();

        ItemDTO itemDTO = ItemDTO.builder()
                .descricao("Produto X")
                .quantidade(2)
                .build();

        Item item = Item.builder()
                .descricao("Produto X")
                .quantidade(2)
                .build();

        Pedido pedidoAtualizado = Pedido.builder()
                .id(pedidoId)
                .status(StatusEnum.CRIADO)
                .itens(List.of(item))
                .build();

        PedidoDTO pedidoDTOResposta = PedidoDTO.builder()
                .id(pedidoId)
                .status(StatusEnum.CRIADO)
                .itens(List.of(itemDTO))
                .build();

        when(pedidoRepository.findById(pedidoId)).thenReturn(Optional.of(pedido));
        itemMapperMock.when(() -> ItemMapper.dtoToEntity(itemDTO)).thenReturn(item);
        when(pedidoRepository.save(pedido)).thenReturn(pedidoAtualizado);
        pedidoMapperMock.when(() -> PedidoMapper.entityToDTO(pedidoAtualizado)).thenReturn(pedidoDTOResposta);

        PedidoDTO resultado = pedidoService.adicionarItens(pedidoId, List.of(itemDTO));

        assertThat(resultado).isEqualTo(pedidoDTOResposta);
        assertThat(pedido.getItens()).containsExactly(item);
        assertThat(item.getPedido()).isEqualTo(pedido);
        verify(pedidoRepository).save(pedido);
    }

    @Test
    void deveLancarExcecaoAoAdicionarItensEmPedidoInexistente() {
        Long pedidoId = 500L;
        ItemDTO itemDTO = ItemDTO.builder().descricao("Produto Y").build();

        when(pedidoRepository.findById(pedidoId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> pedidoService.adicionarItens(pedidoId, List.of(itemDTO)))
                .isInstanceOf(IdNotFoundException.class);

        verify(pedidoRepository, never()).save(any());
    }

    // ---------- removePedido ----------

    @Test
    void deveRemoverPedidoComSucesso() {
        Long pedidoId = 1L;
        Pedido pedido = Pedido.builder().id(pedidoId).build();
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
        StatusEnum novoStatus = StatusEnum.CRIADO;

        Pedido pedido = Pedido.builder().id(pedidoId).status(StatusEnum.CRIADO).build();
        Pedido pedidoAtualizado = Pedido.builder().id(pedidoId).status(novoStatus).build();
        PedidoDTO pedidoDTOResposta = PedidoDTO.builder().id(pedidoId).status(novoStatus).build();

        when(pedidoRepository.findById(pedidoId)).thenReturn(Optional.of(pedido));
        when(pedidoRepository.save(pedido)).thenReturn(pedidoAtualizado);
        pedidoMapperMock.when(() -> PedidoMapper.entityToDTO(pedidoAtualizado)).thenReturn(pedidoDTOResposta);

        PedidoDTO resultado = pedidoService.atualizarStatus(pedidoId, novoStatus);

        assertThat(resultado).isEqualTo(pedidoDTOResposta);
        assertThat(pedido.getStatus()).isEqualTo(novoStatus);
        verify(pedidoRepository).save(pedido);
    }

    @Test
    void deveLancarExcecaoAoAtualizarStatusDePedidoInexistente() {
        Long pedidoId = 42L;
        when(pedidoRepository.findById(pedidoId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> pedidoService.atualizarStatus(pedidoId, StatusEnum.CRIADO))
                .isInstanceOf(IdNotFoundException.class);

        verify(pedidoRepository, never()).save(any());
    }
}