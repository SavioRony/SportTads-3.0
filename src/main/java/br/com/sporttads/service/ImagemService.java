package br.com.sporttads.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.sporttads.model.ImagemModel;
import br.com.sporttads.model.ProdutoModel;
import br.com.sporttads.repository.ImagemRepository;

@Service
public class ImagemService {

	@Autowired
	private ImagemRepository repository;

	private static final String DIRETORIO = "C:\\imagens";

	public ImagemModel salvar(String caminho, ProdutoModel produto) {
		ImagemModel imagem = new ImagemModel(caminho, produto);
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

	public String salvarImagem(MultipartFile arquivo) {
		Path diretorioPath = Paths.get(DIRETORIO);
		Path arquivoPath = diretorioPath.resolve(arquivo.getOriginalFilename());
		try {
			Files.createDirectories(diretorioPath);
			arquivo.transferTo(arquivoPath.toFile());
			System.out.println("Imagem salva com sucesso!");
		} catch (IOException e) {
			throw new RuntimeException("Erro ao tentar salvar imagem");
		}
		return String.valueOf(arquivoPath);
	}
}
