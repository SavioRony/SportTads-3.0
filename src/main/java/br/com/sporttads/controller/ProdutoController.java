package br.com.sporttads.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import br.com.sporttads.model.ProdutoModel;
import br.com.sporttads.service.ProdutoService;


@Controller
@RequestMapping("/produtos")
public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;

	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable Integer id) {
		return new ResponseEntity<>(produtoService.getById(id), HttpStatus.OK);
	}

	@GetMapping("/listaproduto")
	public ModelAndView getAll() {
		List<ProdutoModel> produtos = produtoService.getAll();
		ModelAndView andView = new ModelAndView("Produto/ListaProduto");
		andView.addObject("produtos",produtos);
		return andView;
	}
	
	@GetMapping("/editarproduto/{idproduto}")
	public ModelAndView editar(@PathVariable("idproduto") Integer idproduto) {
		ProdutoModel produto = produtoService.getById(idproduto);
		ModelAndView andView = new ModelAndView("Produto/AlteraProduto");
		andView.addObject("produtoObj", produto);
		
		return andView;
	}
	
	@GetMapping("/inativaativarproduto/{idproduto}")
	public ModelAndView inativaAtiva(@PathVariable("idproduto") Integer idproduto) {
		ProdutoModel produto = produtoService.getById(idproduto);
		ModelAndView andView = new ModelAndView("Produto/InativaAtivaProduto");
		andView.addObject("produtoObj", produto);
		
		return andView;
	}

	@PostMapping("**/salvarproduto")
	public ModelAndView post(ProdutoModel produto) {
		produtoService.save(produto);
		ModelAndView andView = new ModelAndView("Produto/ListaProduto");	
		List<ProdutoModel> produtos = produtoService.getAll();
		andView.addObject("produtos",produtos);
		return andView;
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
	
	@GetMapping("/cadastroproduto")
	public ModelAndView telaCadastro() {
		ModelAndView andView = new ModelAndView("Produto/CadastroProduto");
		return andView;
	}
	
	@GetMapping("/alterarproduto")
	public ModelAndView telaAltera() {
		ModelAndView andView = new ModelAndView("Produto/AlteraProduto");
		return andView;
	}
}
