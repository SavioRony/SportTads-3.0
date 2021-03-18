package br.com.sporttads.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.sporttads.model.ProdutoModel;
import br.com.sporttads.service.ImagemService;
import br.com.sporttads.service.ProdutoService;

@RestController
@RequestMapping("/imagens")
public class ImagemController {

	@Autowired
	private ImagemService imagemService;

	@Autowired
	private ProdutoService produtoService;

	@PostMapping("/{idProduto}")
	public ResponseEntity<?> save(@PathVariable Integer idProduto,
			@RequestParam(name = "arquivos") List<MultipartFile> arquivos) {
		for (int i = 0; i < arquivos.size(); i++) {
			MultipartFile arquivo = arquivos.get(i);
			String caminho = imagemService.salvarImagem(arquivo);
			ProdutoModel produto = produtoService.getById(idProduto);
			imagemService.salvar(caminho, produto);
		}
		return ResponseEntity.ok("Sucesso!");
	}

	@GetMapping()
	public ResponseEntity<?> findAll() {
		return new ResponseEntity<>(imagemService.listar(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable Integer id) {
		return new ResponseEntity<>(imagemService.consultar(id), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		imagemService.deletar(id);
		return ResponseEntity.ok().build();
	}

}
