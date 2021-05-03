package br.com.sporttads.controller;

import br.com.sporttads.model.CarrinhoModel;
import br.com.sporttads.model.ClienteModel;
import br.com.sporttads.model.ProdutoModel;
import br.com.sporttads.model.ItemCarrinhoModel;
import br.com.sporttads.service.CarrinhoService;
import br.com.sporttads.service.ItemCarrinhoService;
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

	@Autowired
	private ItemCarrinhoService itemService;

	private boolean primeiroAcesso = true;

	private CarrinhoModel carrinho = new CarrinhoModel();

	private List<ItemCarrinhoModel> itens = new ArrayList<>();

	@GetMapping()
	public ModelAndView mostrarTela(@AuthenticationPrincipal User user) {
		if(user != null){
			CarrinhoModel carrinho = carrinhoService.populaCarrinho(user);
			if(carrinho == null){
				if(this.carrinho.getItens() == null){
					this.carrinho.setItens(itens);
				}
				carrinhoService.salvaCarrinho(user, this.carrinho);
			}
			if(primeiroAcesso == true){
				primeiroAcesso = false;
				if(this.carrinho != null && this.carrinho.getItens() != null){
					if(this.carrinho.getItens().size() != 0){
						for (ItemCarrinhoModel item : this.carrinho.getItens()){
							if(!verificarSeExiste(item, carrinho)){
								item.setCarrinho(carrinho);
								item.calcularSubtotal();
								itemService.save(item);
							}else{

								carrinhoService.salvaCarrinho(user, carrinho);
							}
						}
						carrinho = carrinhoService.populaCarrinho(user);
						carrinho.calcularTotal();
						this.carrinho = carrinho;
						return new ModelAndView("carrinho");
					}
				}
			}

			carrinho.calcularTotal();
			this.carrinho = carrinhoService.salvaCarrinho(user, carrinho);
		}
		return new ModelAndView("carrinho");
	}

	@GetMapping("/adicionar/{idProduto}")
	public String addProduto(@PathVariable int idProduto, @AuthenticationPrincipal User user) {
		if(user != null){
			CarrinhoModel carrinho = carrinhoService.populaCarrinho(user);
			alterarListaBanco(idProduto, carrinho, true);
		}else{
			alterarListaSessao(idProduto, true);
		}
		return "redirect:/carrinho";
	}

	@GetMapping("/remover/{idProduto}")
	public String removerProduto(@PathVariable int idProduto, @AuthenticationPrincipal User user) {
		int index = getIndex(idProduto);
		if (index >= 0) {
			if(user != null) {
				CarrinhoModel carrinho = carrinhoService.populaCarrinho(user);
				ItemCarrinhoModel itemCarrinhoModel = carrinho.getItens().get(index);
				itemService.delete(itemCarrinhoModel.getId());
				if(carrinho.getItens().size() == 0){
					carrinhoService.delete(carrinho.getId());
				}
			}else{
				this.itens.remove(index);
				this.carrinho.setItens(this.itens);
				this.carrinho.calcularTotal();
			}
		}
		return "redirect:/carrinho";
	}

	@GetMapping("/somarQuant/{idProduto}")
	public String somarProduto(@PathVariable int idProduto, @AuthenticationPrincipal User user) {
		if(user != null){
			CarrinhoModel carrinho = carrinhoService.populaCarrinho(user);
			alterarListaBanco(idProduto,carrinho, true);
		}else{
			alterarListaSessao(idProduto, true);
		}
		return "redirect:/carrinho";
	}

	@GetMapping("/SubQuant/{idProduto}")
	public String subtrairProduto(@PathVariable int idProduto, @AuthenticationPrincipal User user) {
		if(user != null){
			CarrinhoModel carrinho = carrinhoService.populaCarrinho(user);
			alterarListaBanco(idProduto,carrinho, false);
		}else{
			alterarListaSessao(idProduto, false);
		}
		return "redirect:/carrinho";
	}

	public List<ItemCarrinhoModel> getItens() {
		return this.itens;
	}

	public CarrinhoModel getCarrinho() {
		return this.carrinho;
	}

	public boolean verificarSeExiste(ItemCarrinhoModel item, CarrinhoModel car){
		for(ItemCarrinhoModel item1 : car.getItens()){
			if(item.getProduto().getId() == item1.getProduto().getId()){
				item1.setQuantidade(item1.getQuantidade() + item.getQuantidade());
				item1.calcularSubtotal();
				return true;
			}
		}
		return false;
	}

	public int getIndex(int idProduto) {
		int index = -1;
		for (ItemCarrinhoModel item : carrinho.getItens()) {
			if (item.getProduto().getId() == idProduto) {
				return carrinho.getItens().indexOf(item);
			}
		}
		return index;
	}

	public void alterarListaSessao(int idProduto, boolean somar) {
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

	public CarrinhoModel alterarListaBanco(int idProduto,CarrinhoModel carrinho, boolean somar) {
		ProdutoModel produto = produtoService.getById(idProduto);
		ItemCarrinhoModel item = new ItemCarrinhoModel();
		boolean flag = false;
		if (produto.getId() != null) {
			if(carrinho != null && carrinho.getItens() != null) {
				for (ItemCarrinhoModel itemCarrinho : carrinho.getItens()) {
					if (itemCarrinho.getProduto().getId() == produto.getId() && somar) {
						itemCarrinho.setQuantidade(itemCarrinho.getQuantidade() + 1);
						itemCarrinho.calcularSubtotal();
						itemService.save(itemCarrinho);
						flag = true;
						break;
					}

					if (itemCarrinho.getProduto().getId() == produto.getId() && !somar) {
						if (itemCarrinho.getQuantidade() != 1) {
							itemCarrinho.setQuantidade(itemCarrinho.getQuantidade() - 1);
							itemCarrinho.calcularSubtotal();
							itemService.save(itemCarrinho);
						}
						flag = true;
						break;
					}

				}
			}
			if (flag == false) {
				item.setProduto(produto);
				item.setQuantidade(1);
				item.calcularSubtotal();
				item.setCarrinho(carrinho);
				itemService.save(item);
			}
			carrinho.calcularTotal();
		}
		return carrinho;
	}

}
