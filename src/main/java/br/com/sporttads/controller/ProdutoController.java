package br.com.sporttads.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.sporttads.model.ProdutoModel;
import br.com.sporttads.service.ProdutoService;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;

	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable Integer id) {
		return new ResponseEntity<>(produtoService.getById(id), HttpStatus.OK);
	}

	@GetMapping()
	public ResponseEntity<?> getAll() {
		return new ResponseEntity<>(produtoService.getAll(), HttpStatus.OK);
	}

	@PostMapping()
	public ResponseEntity<?> post(@RequestBody ProdutoModel produto) {
		return new ResponseEntity<>(produtoService.post(produto), HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> edit(@PathVariable Integer id, @RequestBody ProdutoModel produto) {
		return new ResponseEntity<>(produtoService.edit(id), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		produtoService.delete(id);
		return ResponseEntity.ok("Produto excluído com sucesso!");
	}
}
