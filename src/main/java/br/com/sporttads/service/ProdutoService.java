package br.com.sporttads.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sporttads.enumeration.StatusEnumeration;
import br.com.sporttads.model.ImagemModel;
import br.com.sporttads.model.ProdutoModel;
import br.com.sporttads.repository.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository repository;

	public ProdutoModel getById(Integer id) {
		return repository.findById(id).orElse(new ProdutoModel());
	}

	public void delete(Integer id) {
		ProdutoModel produto = getById(id);
		produto.setStatus("Inativo");
		repository.save(produto);
	}

	public ProdutoModel save(ProdutoModel produto) {
		return repository.save(produto);
	}

	public List<ProdutoModel> getAll() {
		return repository.findAll();
	}

	public ProdutoModel edit(Integer id) {
		ProdutoModel produtoModel = getById(id);
		return repository.save(produtoModel);
	}
	
	public List<ProdutoModel> findPessoaByName(String nome){
		return repository.findPessoaByName(nome);
	}
	

}
