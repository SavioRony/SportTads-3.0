package br.com.sporttads.repository;

import java.util.List;

import br.com.sporttads.model.UsuarioModel;

public interface UsuarioDao {
	
	void save (UsuarioModel usuarioModel);
	
	void update (UsuarioModel usuarioModel);
	
	UsuarioModel findById(Long id);
	
	List<UsuarioModel> findAll();

}
