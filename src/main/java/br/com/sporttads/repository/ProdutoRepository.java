package br.com.sporttads.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.sporttads.model.ProdutoModel;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoModel, Integer> {
	
	List<ProdutoModel> findByNomeContainingIgnoreCaseAndStatus(String Nome,String status);

	List<ProdutoModel> findByCategoriaAndStatus(String categoria,String ativo);

	List<ProdutoModel> findByStatus(String status);
}
