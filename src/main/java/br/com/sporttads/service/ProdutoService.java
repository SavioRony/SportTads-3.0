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

	public ProdutoModel edit(Integer id, ProdutoModel editProduto) {
		ProdutoModel produtoModel = getById(id);
		if (editProduto.getNome() != null || editProduto.getNome().equals("")) {
			produtoModel.setNome(editProduto.getNome());
		}
		if (editProduto.getCategoria() != null || editProduto.getCategoria().equals("")) {
			produtoModel.setCategoria(editProduto.getCategoria());
		}
		if (editProduto.getDescricao() != null || editProduto.getDescricao().equals("")) {
			produtoModel.setDescricao(editProduto.getDescricao());
		}
		if (editProduto.getEstrelas() != null) {
			produtoModel.setEstrelas(editProduto.getEstrelas());
		}
		if (editProduto.getPreco() != null) {
			produtoModel.setPreco(editProduto.getPreco());
		}
		if (editProduto.getQuantidade() != null) {
			produtoModel.setQuantidade(editProduto.getQuantidade());
		}
		return repository.save(produtoModel);
	}

	public List<ProdutoModel> findProdutoByName(String nome) {
		return repository.findByNomeContainingIgnoreCase(nome);
	}

	public List<ProdutoModel> findByCategoria(String nome) {
		return repository.findByCategoria(nome);
	}
	

}
