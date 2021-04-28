package br.com.sporttads.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import br.com.sporttads.model.ClienteModel;
import br.com.sporttads.model.EnderecoModel;
import br.com.sporttads.model.UsuarioModel;
import br.com.sporttads.repository.ClienteRepository;
import br.com.sporttads.repository.EnderecoRepository;
import br.com.sporttads.repository.UsuarioRepository;

@Service
public class EnderecoService {

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public EnderecoModel getById(Integer id) {
		return this.enderecoRepository.findById(id).orElseThrow(() -> new RuntimeException("Endereço não localizado!"));
	}

	public List<EnderecoModel> getByClienteId(User user) {
		UsuarioModel usuario = this.usuarioRepository.findByEmail(user.getUsername());
		ClienteModel cliente = this.clienteRepository.findByUsuarioId(usuario.getId()).get();
		return this.enderecoRepository.findByClienteId(cliente.getId());
	}

	public EnderecoModel create(EnderecoModel endereco, User user) {
		UsuarioModel usuario = this.usuarioRepository.findByEmail(user.getUsername());
		ClienteModel cliente = this.clienteRepository.findByUsuarioId(usuario.getId()).get();
		endereco.setCliente(cliente);
		return this.enderecoRepository.save(endereco);
	}

	public EnderecoModel createEndereco(EnderecoModel endereco) {
		return this.enderecoRepository.save(endereco);
	}

	public EnderecoModel update(EnderecoModel edit, User user) {
		UsuarioModel usuario = this.usuarioRepository.findByEmail(user.getUsername());
		ClienteModel cliente = this.clienteRepository.findByUsuarioId(usuario.getId()).get();
		edit.setCliente(cliente);
		return this.enderecoRepository.save(edit);
	}

	public void delete(Integer id) {
		EnderecoModel endereco = getById(id);
		this.enderecoRepository.delete(endereco);
	}

}
