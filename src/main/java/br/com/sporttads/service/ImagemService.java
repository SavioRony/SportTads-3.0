package br.com.sporttads.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sporttads.model.ImagemModel;
import br.com.sporttads.repository.ImagemRepository;

@Service
public class ImagemService {

	@Autowired
	private ImagemRepository repository;

	public ImagemModel salvar(String caminho) {
		ImagemModel imagem = new ImagemModel(caminho);
		return repository.save(imagem);
	}

	public List<ImagemModel> listar() {
		return repository.findAll();
	}

	public ImagemModel consultar(Integer id) {
		return repository.findById(id).orElse(new ImagemModel());
	}

	public void deletar(Integer id) {
		repository.deleteById(id);
	}
}
