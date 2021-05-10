package br.com.sporttads.repository;

import br.com.sporttads.model.ClienteModel;
import br.com.sporttads.model.PedidoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<PedidoModel, Integer> {

    List<PedidoModel> findAllByCliente(ClienteModel cliente);
}
