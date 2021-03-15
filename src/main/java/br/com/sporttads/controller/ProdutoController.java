package br.com.sporttads.controller;

import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import br.com.sporttads.model.ProdutoModel;
import br.com.sporttads.service.ProdutoService;


@Controller
@RequestMapping("/produtos")
public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;


	@GetMapping("/listaproduto")
	public ModelAndView getAll() {
		List<ProdutoModel> produtos = produtoService.getAll();
		ModelAndView andView = new ModelAndView("Produto/ListaProduto");
		andView.addObject("produtos",produtos);
		return andView;
	}
	
	@GetMapping("**/editarproduto/{idproduto}")
	public ModelAndView editar(@PathVariable("idproduto") Integer idproduto) {
		ProdutoModel produto = produtoService.getById(idproduto);
		ModelAndView andView = new ModelAndView("Produto/AlteraProduto");
		andView.addObject("produtoObj", produto);
		
		return andView;
	}

	@GetMapping("**/visualizarproduto/{idproduto}")
	public ModelAndView visualizarProduto(@PathVariable("idproduto") Integer idproduto) {
		ProdutoModel produto = produtoService.getById(idproduto);
		ModelAndView andView = new ModelAndView("Produto/VisualizarProduto");
		andView.addObject("produtoObj", produto);

		return andView;
	}
	
	@GetMapping("**/inativaativarproduto/{idproduto}")
	public ModelAndView inativaAtiva(@PathVariable("idproduto") Integer idproduto) {
		ProdutoModel produto = produtoService.getById(idproduto);
		ModelAndView andView = new ModelAndView("Produto/InativaAtivaProduto");
		andView.addObject("produtoObj", produto);
		
		return andView;
	}

	@PostMapping("**/salvarproduto")
	public ModelAndView post(ProdutoModel produto) {
		produtoService.save(produto);
		ModelAndView andView = new ModelAndView("Produto/AlterarImagemProduto");
		return andView;
	}

	@PostMapping("**/inativarreativarproduto")
	public ModelAndView ativaReativar(ProdutoModel produto) {
		produtoService.save(produto);
		ModelAndView andView = new ModelAndView("Produto/ListaProduto");
		List<ProdutoModel> produtos = produtoService.getAll();
		andView.addObject("produtos",produtos);
		return andView;
	}

	@PostMapping("**/pesquisarproduto")
	public ModelAndView pesquisarproduto(@RequestParam("nomepesquisa") String nomepesquisa) {
		ModelAndView andView = new ModelAndView("Produto/ListaProduto");
		andView.addObject("produtos", produtoService.findPessoaByName(nomepesquisa.toUpperCase()));

		return andView;
	}

	@GetMapping("/cadastroproduto")
	public ModelAndView telaCadastro() {
		ModelAndView andView = new ModelAndView("Produto/CadastroProduto");
		return andView;
	}
	
	@PostMapping("**/alterarproduto")
	public ModelAndView telaAltera(ProdutoModel produto) {
		produtoService.save(produto);
		ModelAndView andView = new ModelAndView("Produto/AlterarImagemProduto");	
		return andView;
	}

	@GetMapping("/{idproduto}")
	public ResponseEntity pegarProduto(@PathVariable("idproduto") Integer id){
		return new ResponseEntity(produtoService.getById(id), HttpStatus.OK);
	}

	@GetMapping("/all")
	public ResponseEntity pegarTodos(){
		return new ResponseEntity(produtoService.getAll(), HttpStatus.OK);
	}
	
	@PostMapping("/salvar")
	public ResponseEntity salvar(@RequestBody ProdutoModel produto){
		return new ResponseEntity(produtoService.save(produto), HttpStatus.OK);
	}

	@PutMapping("/alterar/{idproduto}")
	public ResponseEntity alterar(@RequestBody ProdutoModel produto){
		return new ResponseEntity(produtoService.save(produto), HttpStatus.OK);
	}

	@DeleteMapping("/delete/{idproduto}")
	public void apagar(@PathVariable("idproduto") Integer id){
		produtoService.delete(id);
	}

}
