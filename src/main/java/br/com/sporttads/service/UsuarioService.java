package br.com.sporttads.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sporttads.model.UsuarioModel;
import br.com.sporttads.repository.UsuarioRepository;

@Service
@Transactional(readOnly = false)
public class UsuarioService implements UserDetailsService {

	@Autowired
	private UsuarioRepository repository;

	public void salvar(UsuarioModel usuario) {
		String crypt = new BCryptPasswordEncoder().encode(usuario.getSenha());
		usuario.setSenha(crypt);
		repository.save(usuario);
	}

	public void editar(UsuarioModel usuario) {
		String crypt = new BCryptPasswordEncoder().encode(usuario.getSenha());
		usuario.setSenha(crypt);
		repository.save(usuario);

	}

	public void inativaAtivar(UsuarioModel usuario) {
		repository.save(usuario);
	}

	@Transactional(readOnly = true)
	public UsuarioModel findById(Long id) {
		return repository.findById(id).get();
		
	}

	@Transactional(readOnly = true)
	public List<UsuarioModel> findAll() {
		
		return repository.findAll();
	}

	@Transactional(readOnly = true)
	public UsuarioModel findByEmail(String email) {
		return repository.findByEmail(email);
	}

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UsuarioModel usuario = findByEmail(username);
		if(usuario.getStatus().equals("Inativo")){
			throw new UsernameNotFoundException("Usuario Inativo");
		}
		return new User(
				usuario.getEmail(),
				usuario.getSenha(),
				AuthorityUtils.createAuthorityList(usuario.getTipoDeFuncionario())
		);
	}
}
