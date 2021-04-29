package br.com.sporttads.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sporttads.model.EnderecoModel;

@Repository
public interface EnderecoRepository extends JpaRepository<EnderecoModel, Integer> {

	List<EnderecoModel> findByClienteIdAndStatus(Long id, String status);

}
