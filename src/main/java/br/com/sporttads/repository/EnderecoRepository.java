package br.com.sporttads.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import br.com.sporttads.model.ProdutoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.sporttads.model.EnderecoModel;

@Repository
public interface EnderecoRepository extends JpaRepository<EnderecoModel, Integer> {

	List<EnderecoModel> findByClienteIdAndStatus(Long id, String status);

    List<EnderecoModel>  findByClienteId(Long id);
}
