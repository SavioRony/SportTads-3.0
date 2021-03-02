package br.com.sporttads.controller;

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

import br.com.sporttads.service.ImagemService;
import br.com.sporttads.utils.DiscoUtils;

@RestController
@RequestMapping("/imagens")
public class ImagemController {

	@Autowired
	private ImagemService service;

	@Autowired
	private DiscoUtils disc;

	@PostMapping()
	public ResponseEntity<?> save(@RequestParam MultipartFile foto) {
		String caminho = disc.salvar(foto);
		return new ResponseEntity<>(service.salvar(caminho), HttpStatus.OK);
	}

	@GetMapping()
	public ResponseEntity<?> findAll() {
		return new ResponseEntity<>(service.listar(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable Integer id) {
		return new ResponseEntity<>(service.consultar(id), HttpStatus.OK);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		service.deletar(id);
		return ResponseEntity.ok("Imagem excluída com sucesso!");
	}

}
