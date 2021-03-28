package br.com.sporttads.repository;

import org.springframework.stereotype.Repository;

import br.com.sporttads.model.UsuarioModel;

@Repository
public class UsuarioRepository extends AbstrataDao<UsuarioModel,Long> implements UsuarioDao {

	

}
