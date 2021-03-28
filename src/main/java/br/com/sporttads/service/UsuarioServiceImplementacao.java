package br.com.sporttads.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sporttads.model.UsuarioModel;
import br.com.sporttads.repository.UsuarioDao;

@Service
@Transactional(readOnly = false)
public class UsuarioServiceImplementacao implements UsuarioService {

	@Autowired
	private UsuarioDao dao;

	@Override
	public void salvar(UsuarioModel usuarioModel) {
		dao.save(usuarioModel);

	}

	@Override
	public void editar(UsuarioModel usuarioModel) {
		dao.update(usuarioModel);

	}

	@Override
	@Transactional(readOnly = true)
	public UsuarioModel findById(Long id) {

		return dao.findById(id);
		
	}

	@Override
	@Transactional(readOnly = true)
	public List<UsuarioModel> findAll() {
		
		return dao.findAll();
	}

}
