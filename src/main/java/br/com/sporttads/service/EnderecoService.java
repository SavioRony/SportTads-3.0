package br.com.sporttads.service;

import java.util.ArrayList;
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
		ClienteModel cliente = this.clienteRepository.findByUsuarioId(usuario.getId()).orElse(new ClienteModel());
		if(cliente.hasNotId()){
			return new ArrayList<EnderecoModel>();
		}
		return this.enderecoRepository.findByClienteIdAndStatus(cliente.getId(), "Ativo");
	}

		
	public EnderecoModel create(EnderecoModel endereco, User user) {
		UsuarioModel usuario = this.usuarioRepository.findByEmail(user.getUsername());
		ClienteModel cliente = this.clienteRepository.findByUsuarioId(usuario.getId()).get();
		endereco.setCliente(cliente);
		List<EnderecoModel> enderecos = this.enderecoRepository.findAll();
		for (EnderecoModel e : enderecos) {
			if (endereco.getIsPadrao() && e.getId() != endereco.getId()) {
				e.setIsPadrao(false);
			}
		}
		return this.enderecoRepository.save(endereco);
	}

	public EnderecoModel createEndereco(EnderecoModel endereco) {
		return this.enderecoRepository.save(endereco);
	}

	public EnderecoModel update(EnderecoModel edit, User user) {
		UsuarioModel usuario = this.usuarioRepository.findByEmail(user.getUsername());
		ClienteModel cliente = this.clienteRepository.findByUsuarioId(usuario.getId()).get();
		edit.setCliente(cliente);
		List<EnderecoModel> enderecos = this.enderecoRepository.findAll();
		for (EnderecoModel e : enderecos) {
			if (edit.getIsPadrao() && e.getId() != edit.getId()) {
				e.setIsPadrao(false);
			}
		}
		return this.enderecoRepository.save(edit);
	}

	public void delete(Integer id) {
		EnderecoModel endereco = getById(id);
		endereco.setStatus("Inativo");
		this.enderecoRepository.save(endereco);
	}

	public boolean hasEnderecoPrincipal(User user){
		UsuarioModel usuario = this.usuarioRepository.findByEmail(user.getUsername());
		ClienteModel cliente = this.clienteRepository.findByUsuarioId(usuario.getId()).get();
		return enderecoRepository.findByClienteId(cliente.getId()).size() > 0;
	}

}
