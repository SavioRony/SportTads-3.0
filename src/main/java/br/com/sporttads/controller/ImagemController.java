package br.com.sporttads.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import br.com.sporttads.model.ProdutoModel;
import br.com.sporttads.service.ImagemService;
import br.com.sporttads.service.ProdutoService;
import br.com.sporttads.utils.DiscoUtils;

@Controller
@RequestMapping("/imagens")
public class ImagemController {

	@Autowired
	private ImagemService imagemService;

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private DiscoUtils disc;

	@PostMapping("/{idProduto}")
	public ResponseEntity<?> save(@PathVariable Integer idProduto, @RequestParam MultipartFile imagens) {
		ProdutoModel produto = produtoService.getById(idProduto);
		String caminho = disc.salvar(imagens);
		return new ResponseEntity<>(imagemService.salvar(caminho, produto), HttpStatus.OK);
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
