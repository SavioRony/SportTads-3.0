package br.com.sporttads.controller;

import java.util.ArrayList;
import java.util.List;

import br.com.sporttads.model.CarrinhoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import br.com.sporttads.model.ProdutoModel;
import br.com.sporttads.service.ProdutoService;

import javax.servlet.http.HttpServletResponse;

@Controller
public class indexController {

	@Autowired
	private ProdutoService produtoService;
	
			
	@RequestMapping("/")
	public ModelAndView getAllAtivos() {
		List<ProdutoModel> produtos = produtoService.getAllAtivo();
		for (int i = 0; i < produtos.size(); i++){
			if(produtos.get(i).getQuantidade() <= 0){
				produtos.remove(i);
			}
		}
		ModelAndView andView = new ModelAndView("index");
		andView.addObject("produtos",produtos);
		return andView;
	}



	@GetMapping ("/pesquisar-por-categotia")
	public ModelAndView pesquisarPorCategoria(@RequestParam("categoria") String categoria) {
		ModelAndView andView = new ModelAndView("index");
		List<ProdutoModel> produtos;
		if(categoria.equals("Todos")){
			produtos = produtoService.getAllAtivo();
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

	// abrir pagina login
	@GetMapping({"/login"})
	public String login() {
		return "login";
	}

	// login invalido
	@GetMapping({"/login-error"})
	public String loginError(ModelMap model) {
		model.addAttribute("alerta", "erro");
		model.addAttribute("titulo", "Crendenciais inválidas!");
		model.addAttribute("texto", "Login ou senha incorretos, tente novamente.");
		model.addAttribute("subtexto", "Acesso permitido apenas para cadastros já ativados.");
		return "login";
	}

	// acesso negado
	@GetMapping({"/acesso-negado"})
	public String acessoNegado(ModelMap model, HttpServletResponse resp) {
		model.addAttribute("status", resp.getStatus());
		model.addAttribute("error", "Acesso Negado");
		model.addAttribute("message", "Você não tem permissão para acesso a esta área ou ação.");
		return "error";
	}

	@GetMapping({"/home"})
	public String home() {
		return "home";
	}

	// abrir pagina login
	@GetMapping({"/logout"})
	public String logout() {
		CarrinhoController.setCarrinho(new CarrinhoModel());
		CarrinhoController.setTotal(0);
		CarrinhoController.setFretes(new ArrayList<>());
		CarrinhoController.setItens(new ArrayList<>());
		return "redirect:/";
	}
}
