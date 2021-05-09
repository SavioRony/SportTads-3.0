package br.com.sporttads.repository;

import br.com.sporttads.model.CartaoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartaoRepository extends JpaRepository<CartaoModel, Integer> {

}

