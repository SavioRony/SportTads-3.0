package br.com.sporttads.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.sporttads.model.ClienteModel;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteModel, Long> {

    @Query("select c from ClienteModel c where c.usuario.email like :email")
    Optional<ClienteModel> findByIdUsuario(String email);
}
