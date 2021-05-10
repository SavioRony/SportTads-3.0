package br.com.sporttads.service;

import br.com.sporttads.model.ItemPedidoModel;
import br.com.sporttads.model.PedidoModel;
import br.com.sporttads.repository.ItemPedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemPedidoService {

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    public List<ItemPedidoModel> saveAll(List<ItemPedidoModel> itensPedido) {
       return itemPedidoRepository.saveAll(itensPedido);
    }

    public List<ItemPedidoModel> getPedido(PedidoModel pedido) {
        return itemPedidoRepository.findAllByPedido(pedido);
    }
}
