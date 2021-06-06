package br.com.sporttads.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sporttads.model.ImagemModel;
import br.com.sporttads.model.ProdutoModel;
import br.com.sporttads.repository.ImagemRepository;

@Service
public class ImagemService {

	@Autowired
	private ImagemRepository repository;

	public List<ImagemModel> listar() {
		return repository.findAll();
	}

	public ImagemModel consultar(Integer id) {
		return repository.findById(id).orElse(new ImagemModel());
	}

	public void deletar(Integer id) {
		repository.deleteById(id);
	}
	
	public ImagemModel save(ImagemModel imagem) {
		return repository.save(imagem);
	}
	
	public List<ImagemModel> findByIdProduto(Integer idProduto) {
		return repository.findByIdProduto(idProduto).orElse(new ArrayList<>());
	}
	
}
