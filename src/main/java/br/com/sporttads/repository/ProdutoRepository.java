package br.com.sporttads.repository;

import br.com.sporttads.model.ProdutoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoModel, Integer> {

    ProdutoModel findById(int id);
    ProdutoModel deleteById(int id);
    ProdutoModel save(ProdutoModel produto);
}
