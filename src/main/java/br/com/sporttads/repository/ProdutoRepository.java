package br.com.sporttads.repository;

import br.com.sporttads.model.ProdutoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoModel, Integer> {

	List<ProdutoModel> findByNomeContainingIgnoreCaseAndStatus(String Nome, String status);

	List<ProdutoModel> findByCategoriaAndStatus(String categoria, String ativo);

	List<ProdutoModel> findByStatus(String status);

    Optional<List<ProdutoModel>> findAllByQuantidadeLessThanEqual(Integer i);
}
