package br.com.sporttads.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.sporttads.model.ProdutoModel;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoModel, Integer> {
	
	@Query(value="Select * from tb_produto p where p.nome like %?1%", nativeQuery = true)
	List<ProdutoModel> findPessoaByName(String Nome);
}
