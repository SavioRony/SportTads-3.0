package br.com.sporttads.repository;

import br.com.sporttads.model.ItemPedidoModel;
import br.com.sporttads.model.PedidoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedidoModel, Integer> {
    List<ItemPedidoModel> findAllByPedido(PedidoModel pedido);
}
