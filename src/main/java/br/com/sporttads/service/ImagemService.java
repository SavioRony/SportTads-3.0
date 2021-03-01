package br.com.sporttads.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sporttads.dto.ImagemDTO;
import br.com.sporttads.model.ImagemModel;
import br.com.sporttads.repository.ImagemRepository;

@Service
public class ImagemService {

	@Autowired
	private ImagemRepository repository;

	public ImagemModel salvar(ImagemDTO dto) {
		return repository.save(new ImagemModel(dto));
	}

	public List<ImagemModel> listar() {
		return repository.findAll();
	}

	public ImagemModel consultar(Integer id) {
		return repository.findById(id).orElse(new ImagemModel());
	}

	public ImagemModel alterar(Integer id, ImagemDTO dto) {
		ImagemModel imagem = consultar(id);
		imagem.setNome(dto.getNome());
		imagem.setImagem(dto.getImagem());
		return imagem;
	}

	public void deletar(Integer id) {
		repository.deleteById(id);
	}
}
