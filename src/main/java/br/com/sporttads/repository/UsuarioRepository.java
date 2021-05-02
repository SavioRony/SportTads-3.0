package br.com.sporttads.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.sporttads.model.UsuarioModel;

public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long> {

	@Query("select u from UsuarioModel u where u.email like :email")
	UsuarioModel findByEmail(@Param("email") String email);

	@Query("select u from UsuarioModel u where u.email like :email AND u.status = 'Ativo'")
	Optional<UsuarioModel> findByEmailEAtivo(String email);

	@Query("select u from UsuarioModel u where u.tipo = 'Administrador' OR u.tipo = 'Estoquista'")
	List<UsuarioModel> findByTipoUsuario();
}
