package br.com.sporttads.repository;

import br.com.sporttads.model.ItemCarrinhoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemCarrinhoRepository extends JpaRepository<ItemCarrinhoModel, Integer> {
}
