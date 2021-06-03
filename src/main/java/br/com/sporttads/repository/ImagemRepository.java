package br.com.sporttads.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.sporttads.model.ImagemModel;
import br.com.sporttads.model.ProdutoModel;

public interface ImagemRepository extends JpaRepository<ImagemModel, Integer> {
	
	@Query(value="Select * from tb_imagem p where p.id_produto = ?1", nativeQuery = true)
	Optional<List<ImagemModel>> findByIdProduto(Integer idProduto);

}
