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

	/**
	 * Metodo para lista os produtos que estão ativos e com estoque positivo.
	 * @return ModelAndView - Tela de principal com os produtos.
	 */
	@RequestMapping("/")
	public ModelAndView getAllAtivos() {
		return new ModelAndView("index","produtos", produtoService.getAllAtivo());
	}

	/**
	 * Metodo de busca por categoria de produtos
	 * @param categoria
	 * @return ModelAndView - Tela principal com os produtos encontrados pela categoria
	 */
	@GetMapping ("/pesquisar-por-categotia")
	public ModelAndView pesquisarPorCategoria(@RequestParam("categoria") String categoria) {
		List<ProdutoModel> produtos;
		if(categoria.equals("Todos")){
			produtos = produtoService.getAllAtivo();
		}else{
			produtos = produtoService.findByCategoria(categoria);
		}

		return new ModelAndView("index", "produtos", produtos);
	}

	/**
	 * Metodo para busca os produtos ativo pelo nome
	 * @param nome
	 * @return ModelAndView - Tela principal com os produtos encontrados pelo nome
	 */
	@GetMapping ("/pesquisar-por-nome")
	public ModelAndView pesquisarPorNome(@RequestParam("nome") String nome) {
		return new ModelAndView("index","produtos", produtoService.findProdutoByName(nome));
	}


	/**
	 * Metodo para abri a tela de login
	 * @return String - tela de login
	 */
	@GetMapping({"/login"})
	public String login() {
		return "login";
	}

	/**
	 * Metodo para tela de login com erro
	 * @param model
	 * @return String - tela de login com erro
	 */
	@GetMapping({"/login-error"})
	public String loginError(ModelMap model) {
		model.addAttribute("alerta", "erro");
		model.addAttribute("titulo", "Crendenciais inválidas!");
		model.addAttribute("texto", "Login ou senha incorretos, tente novamente.");
		model.addAttribute("subtexto", "Acesso permitido apenas para cadastros já ativados.");
		return "login";
	}

	/**
	 * Metodo de tela com o acesso negado
	 * @param model
	 * @param resp
	 * @return String - tela de erro
	 */
	@GetMapping({"/acesso-negado"})
	public String acessoNegado(ModelMap model, HttpServletResponse resp) {
		model.addAttribute("status", resp.getStatus());
		model.addAttribute("error", "Acesso Negado");
		model.addAttribute("message", "Você não tem permissão para acesso a esta área ou ação.");
		return "error";
	}

	/**
	 * Metodo para abrir o home de usuario do tipo Adiministrado e Estoquista
	 * @return String - tela de home
	 */
	@GetMapping({"/home"})
	public String home() {
		return "home";
	}


	/**
	 * Metodo para fazer logout no sistema
	 * @return String - tela do index
	 */
	@GetMapping({"/logout"})
	public String logout() {
		//Limpar o carrinho, total, Fretes e itens do carrinho.
		CarrinhoController.setCarrinho(new CarrinhoModel());
		CarrinhoController.setTotal(0);
		CarrinhoController.setFretes(new ArrayList<>());
		CarrinhoController.setItens(new ArrayList<>());
		return "redirect:/";
	}
}
