package br.com.sporttads.repository;

import br.com.sporttads.model.CarrinhoModel;
import br.com.sporttads.model.ClienteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarrinhoRepository extends JpaRepository<CarrinhoModel, Integer> {

    List<CarrinhoModel> findByCliente(ClienteModel cliente);
}
