package br.com.sporttads.controller;

import br.com.sporttads.model.CarrinhoModel;
import br.com.sporttads.model.ProdutoModel;
import br.com.sporttads.model.ItemCarrinhoModel;
import br.com.sporttads.service.CarrinhoService;
import br.com.sporttads.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/carrinho")
@Scope("session")
public class CarrinhoController {

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private CarrinhoService carrinhoService;

	private CarrinhoModel carrinho = new CarrinhoModel();

	private List<ItemCarrinhoModel> itens = new ArrayList<>();

	@GetMapping()
	public ModelAndView mostrarTela(@AuthenticationPrincipal User user) {
		this.carrinho = this.carrinhoService.populaCarrinho(user);
		this.itens = this.carrinho.getItens();
		carrinhoService.salvaCarrinho(user, carrinho);
		return new ModelAndView("carrinho");
	}

	@GetMapping("/adicionar/{idProduto}")
	public String addProduto(@PathVariable int idProduto, @AuthenticationPrincipal User user) {
		this.carrinho = this.carrinhoService.populaCarrinho(user);
		this.itens = this.carrinho.getItens();
		alterarLista(idProduto, true);
		carrinhoService.salvaCarrinho(user, carrinho);
		return "redirect:/carrinho";
	}

	@GetMapping("/remover/{idProduto}")
	public String removerProduto(@PathVariable int idProduto, @AuthenticationPrincipal User user) {
		int index = getIndex(idProduto);
		if (index >= 0) {
			this.itens.remove(index);
			this.carrinho.setItens(this.itens);
			this.carrinho.calcularTotal();
			this.carrinhoService.salvaCarrinho(user, carrinho);
		}

		if (this.carrinhoService.findAll().size() > 0) {

		}

		return "redirect:/carrinho";
	}

	@GetMapping("/somarQuant/{idProduto}")
	public String somarProduto(@PathVariable int idProduto, @AuthenticationPrincipal User user) {
		alterarLista(idProduto, true);
		carrinhoService.salvaCarrinho(user, carrinho);
		return "redirect:/carrinho";
	}

	@GetMapping("/SubQuant/{idProduto}")
	public String subtrairProduto(@PathVariable int idProduto, @AuthenticationPrincipal User user) {
		alterarLista(idProduto, false);
		carrinhoService.salvaCarrinho(user, carrinho);
		return "redirect:/carrinho";
	}

	public List<ItemCarrinhoModel> getItens() {
		return this.itens;
	}

	public CarrinhoModel getCarrinho() {
		return this.carrinho;
	}

	public int getIndex(int idProduto) {
		int index = -1;
		for (ItemCarrinhoModel item : this.itens) {
			if (item.getProduto().getId() == idProduto) {
				return itens.indexOf(item);
			}
		}
		return index;
	}

	public void alterarLista(int idProduto, boolean somar) {
		ProdutoModel produto = produtoService.getById(idProduto);
		ItemCarrinhoModel item = new ItemCarrinhoModel();
		boolean flag = false;
		if (produto.getId() != null) {
			for (ItemCarrinhoModel itemCarrinho : this.itens) {
				if (itemCarrinho.getProduto().getId() == produto.getId() && somar) {
					itemCarrinho.setQuantidade(itemCarrinho.getQuantidade() + 1);
					itemCarrinho.calcularSubtotal();
					flag = true;
					break;
				}

				if (itemCarrinho.getProduto().getId() == produto.getId() && !somar) {
					if (itemCarrinho.getQuantidade() != 1) {
						itemCarrinho.setQuantidade(itemCarrinho.getQuantidade() - 1);
						itemCarrinho.calcularSubtotal();
					}
					flag = true;
					break;
				}

			}
			if (flag == false) {
				item.setProduto(produto);
				item.setQuantidade(1);
				item.calcularSubtotal();
				this.itens.add(item);
			}
			this.carrinho.setItens(this.itens);
			this.carrinho.calcularTotal();
		}
	}

}
