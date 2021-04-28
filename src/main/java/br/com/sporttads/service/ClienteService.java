package br.com.sporttads.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;



import br.com.sporttads.model.ClienteModel;
import br.com.sporttads.model.ProdutoModel;
import br.com.sporttads.model.UsuarioModel;
import br.com.sporttads.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;

	public void salvar (ClienteModel cliente) {
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
		
		cliente.setNomeCompleto(cliente.getNomeCompleto());
		cliente.setCpf(cliente.getCpf());
		cliente.setDataNascimento(cliente.getDataNascimento()); 
		cliente.setTelPrincipal(cliente.getTelPrincipal());

		repository.save(cliente);

	}

	public ClienteModel buscaPorEmailUser(String email){
		return repository.findByIdUsuario(email).orElse(new ClienteModel());
	}
	
	public ClienteModel findBycpf(String cpf){
		return repository.findBycpf(cpf).orElse(new ClienteModel());
	}
	
/*
	public boolean findByCpf(String cpf) {
		return repository.findBycpf(cpf); 
	}*/

	public  boolean validaNome(String nome) {
		boolean valido = false;
		if (nome.split(" ").length >= 2) {
			String[] splitNome = nome.split(" ");
			for (int i = 0; i < splitNome.length; i++) {
				if (splitNome[i].length() >= 3) {
					valido = true;
				} else {
					valido = false;
				}
			}
		}
		return valido;
	}
}
