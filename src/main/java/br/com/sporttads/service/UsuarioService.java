package br.com.sporttads.service;

import java.util.List;

import br.com.sporttads.model.UsuarioModel;

public interface UsuarioService {

	void salvar(UsuarioModel usuarioModel);

	void editar(UsuarioModel usuarioModel);
	
	UsuarioModel findById(Long id);

	List<UsuarioModel> findAll();

}
