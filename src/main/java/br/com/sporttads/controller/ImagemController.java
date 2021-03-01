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

import br.com.sporttads.dto.ImagemDTO;
import br.com.sporttads.service.ImagemService;

@RestController
@RequestMapping("/imagens")
public class ImagemController {

	@Autowired
	private ImagemService service;

	@PostMapping()
	public ResponseEntity<?> save(@RequestBody ImagemDTO dto) {
		return new ResponseEntity<>(service.salvar(dto), HttpStatus.OK);
	}

	@GetMapping()
	public ResponseEntity<?> findAll() {
		return new ResponseEntity<>(service.listar(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable Integer id) {
		return new ResponseEntity<>(service.consultar(id), HttpStatus.OK);
	}

	@PutMapping("{id}")
	public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody ImagemDTO dto) {
		return new ResponseEntity<>(service.alterar(id, dto), HttpStatus.OK);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		service.deletar(id);
		return ResponseEntity.ok("IMAGEM EXCLUÍDA COM SUCESSO!");
	}
}
