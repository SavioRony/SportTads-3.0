package br.com.sporttads.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sporttads.model.ClienteModel;
import br.com.sporttads.model.EnderecoModel;
import br.com.sporttads.repository.ClienteRepository;
import br.com.sporttads.repository.EnderecoRepository;

@Service
public class EnderecoService {

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	public List<EnderecoModel> getAll() {
		return this.enderecoRepository.findAll();
	}

	public EnderecoModel getById(Integer id) {
		return this.enderecoRepository.findById(id).orElseThrow(() -> new RuntimeException("Endereço não localizado!"));
	}

	public List<EnderecoModel> getByClienteId(Long id) {
		return this.enderecoRepository.findByClienteId(id);
	}

	public EnderecoModel create(EnderecoModel endereco, Long id) {
		ClienteModel cliente = this.clienteRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Cliente não localizado!"));
		endereco.setCliente(cliente);
		return this.enderecoRepository.save(endereco);
	}

	public EnderecoModel createEndereco(EnderecoModel endereco) {
		return this.enderecoRepository.save(endereco);
	}

	public EnderecoModel update(EnderecoModel edit, Integer id) {
		EnderecoModel endereco = getById(id);
		endereco.setCep(edit.getComplemento());
		endereco.setBairro(edit.getBairro());
		endereco.setNumero(edit.getNumero());
		endereco.setLocalidade(edit.getLocalidade());
		endereco.setLogradouro(edit.getLogradouro());
		endereco.setComplemento(edit.getComplemento());
		endereco.setUf(edit.getUf());
		return this.enderecoRepository.save(endereco);
	}

	public void delete(Integer id) {
		EnderecoModel endereco = getById(id);
		this.enderecoRepository.delete(endereco);
	}

}
