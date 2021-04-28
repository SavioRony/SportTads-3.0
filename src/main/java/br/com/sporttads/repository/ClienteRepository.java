package br.com.sporttads.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.sporttads.model.ClienteModel;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteModel, Long> {

	@Query("select c from ClienteModel c where c.usuario.email like :email")
	Optional<ClienteModel> findByIdUsuario(String email);

	Optional<ClienteModel> findBycpf(String cpf);

	Optional<ClienteModel> findByUsuarioId(Long id);

}
