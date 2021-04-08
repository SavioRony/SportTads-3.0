package br.com.sporttads.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;



import br.com.sporttads.model.ClienteModel;
import br.com.sporttads.model.UsuarioModel;
import br.com.sporttads.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;

	public void salvar (ClienteModel cliente) {
		String crypt = new BCryptPasswordEncoder().encode(cliente.getSenha());
		cliente.setSenha(crypt);
		repository.save(cliente);
	}
	

	public ClienteModel findById(Long id) {
		Optional<ClienteModel> obj = repository.findById(id);
		return obj.get();

	}
	
	public List<ClienteModel> findAll() {
		return repository.findAll();
	}
	
	
	public void editar(ClienteModel cliente) {
		String crypt = new BCryptPasswordEncoder().encode(cliente.getSenha());
		
		cliente.setNomeCompleto(cliente.getNomeCompleto());
		cliente.setCpf(cliente.getCpf());
		cliente.setDataNascimento(cliente.getDataNascimento());
		cliente.setTelPrincipal(cliente.getTelPrincipal());
		cliente.setEmail(cliente.getEmail());
		cliente.setSenha(crypt);
		cliente.setEstado(cliente.getEstado());
		cliente.setCidade(cliente.getCidade());
		cliente.setBairro(cliente.getBairro());
		cliente.setCep(cliente.getCep());
		cliente.setEndPrincipal(cliente.getEndPrincipal());
		cliente.setNumero(cliente.getNumero());
		cliente.setComplemento(cliente.getComplemento());
					
		repository.save(cliente);

	}

}
