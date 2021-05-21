package br.com.sporttads.repository;

import br.com.sporttads.model.ClienteModel;
import br.com.sporttads.model.PedidoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository<PedidoModel, Integer> {

    Optional<List<PedidoModel>> findAllByClienteOrderByIdDesc(ClienteModel cliente);
}
