package br.com.sporttads.repository;

import br.com.sporttads.model.ProdutoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository {

    ProdutoModel findById(Integer id);

    ProdutoModel deleteById(Integer id);

    ProdutoModel save(ProdutoModel produto);
}
