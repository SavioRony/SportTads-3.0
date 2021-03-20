package br.com.sporttads.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import br.com.sporttads.model.ProdutoModel;
import br.com.sporttads.service.ProdutoService;

@Controller
public class indexController {

	@Autowired
	private ProdutoService produtoService;
	
			
	@RequestMapping("/")
	public ModelAndView getAll() {
		List<ProdutoModel> produtos = produtoService.getAll();
		ModelAndView andView = new ModelAndView("index");
		andView.addObject("produtos",produtos);
		return andView;
	}

	@GetMapping ("/pesquisar-por-categotia")
	public ModelAndView pesquisarPorCategoria(@RequestParam("categoria") String categoria) {
		ModelAndView andView = new ModelAndView("index");
		List<ProdutoModel> produtos;
		if(categoria.equals("Todos")){
			produtos = produtoService.getAll();
		}else{
			produtos = produtoService.findByCategoria(categoria);
		}
		andView.addObject("produtos", produtos);

		return andView;
	}

	@GetMapping ("/pesquisar-por-nome")
	public ModelAndView pesquisarPorNome(@RequestParam("nome") String nome) {
		ModelAndView andView = new ModelAndView("index");
		andView.addObject("produtos", produtoService.findProdutoByName(nome));
		return andView;
	}
}
