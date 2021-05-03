package br.com.sporttads.repository;

import java.util.List;
import java.util.Optional;

import br.com.sporttads.model.CarrinhoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sporttads.model.ItemCarrinhoModel;

@Repository
public interface ItemCarrinhoRepository extends JpaRepository<ItemCarrinhoModel, Integer> {

	Optional<List<ItemCarrinhoModel>> findByCarrinho(CarrinhoModel carrinho);

}
