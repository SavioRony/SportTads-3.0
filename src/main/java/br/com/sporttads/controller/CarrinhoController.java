package br.com.sporttads.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.sporttads.model.CarrinhoModel;
import br.com.sporttads.model.FreteModel;
import br.com.sporttads.model.ItemCarrinhoModel;
import br.com.sporttads.model.ProdutoModel;
import br.com.sporttads.service.CarrinhoService;
import br.com.sporttads.service.FreteService;
import br.com.sporttads.service.ItemCarrinhoService;
import br.com.sporttads.service.ProdutoService;

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

	@Autowired
	private FreteService freteService;

	private boolean primeiroAcesso = true;

	private static CarrinhoModel carrinho = new CarrinhoModel();

	private static List<ItemCarrinhoModel> itens = new ArrayList<>();

	private static List<FreteModel> fretes = new ArrayList<>();

	private static double total;

	@GetMapping()
	public ModelAndView mostrarTela(@AuthenticationPrincipal User user) {
		if (this.carrinho.getCep() != null && !("".equals(this.carrinho.getCep()))) {
			this.opcoesFrete(this.carrinho.getCep());
		}
		if (user != null) {
			CarrinhoModel carrinho = carrinhoService.populaCarrinho(user);
			if (carrinho == null) {
				if (this.carrinho.getItens() == null) {
					this.carrinho.setItens(itens);
				}
				carrinhoService.salvaCarrinho(user, this.carrinho);
			}
			if (primeiroAcesso == true) {
				primeiroAcesso = false;
				if (this.carrinho != null && this.carrinho.getItens() != null) {
					if (this.carrinho.getItens().size() != 0) {
						for (ItemCarrinhoModel item : this.carrinho.getItens()) {
							if (!verificarSeExiste(item, carrinho)) {
								item.setCarrinho(carrinho);
								item.calcularSubtotal();
								itemService.save(item);
							} else {
								carrinhoService.salvaCarrinho(user, carrinho);
							}
						}
						carrinho = carrinhoService.populaCarrinho(user);
						carrinho.calcularTotal();
						this.carrinho = carrinho;

						this.carrinho.setValorFrete(this.carrinho.getTotal() * this.carrinho.getFrete().getTaxa());
						this.total = this.carrinho.getTotal() + this.carrinho.getValorFrete();
						return new ModelAndView("carrinho");
					}
				}
			}
			if (carrinho.getItens() != null) {
				carrinho.calcularTotal();
			}
			this.carrinho = carrinhoService.salvaCarrinho(user, carrinho);
		}
		if(this.carrinho.getFrete() != null){
			this.carrinho.setValorFrete(this.carrinho.getTotal() * this.carrinho.getFrete().getTaxa());
			for (FreteModel frete : fretes){
				frete.setValorFrete(this.carrinho.getTotal() * frete.getTaxa());
			}
		}
		this.total = this.carrinho.getTotal() + this.carrinho.getValorFrete();
		return new ModelAndView("carrinho");
	}

	@GetMapping("/adicionar/{idProduto}")
	public String addProduto(@PathVariable int idProduto, @AuthenticationPrincipal User user) {
		if (user != null) {
			CarrinhoModel carrinho = carrinhoService.populaCarrinho(user);
			alterarListaBanco(idProduto, carrinho, true);
		} else {
			alterarListaSessao(idProduto, true);
		}
		return "redirect:/carrinho";
	}

	@GetMapping("/remover/{idProduto}")
	public String removerProduto(@PathVariable int idProduto, @AuthenticationPrincipal User user) {
		int index = getIndex(idProduto);
		if (index >= 0) {
			if (user != null) {
				CarrinhoModel carrinho = carrinhoService.populaCarrinho(user);
				ItemCarrinhoModel itemCarrinhoModel = carrinho.getItens().get(index);
				itemService.delete(itemCarrinhoModel.getId());
			} else {
				this.itens.remove(index);
				this.carrinho.setItens(this.itens);
				this.carrinho.calcularTotal();
			}
		}
		return "redirect:/carrinho";
	}

	@GetMapping("/somarQuant/{idProduto}")
	public String somarProduto(@PathVariable int idProduto, @AuthenticationPrincipal User user) {
		if (user != null) {
			CarrinhoModel carrinho = carrinhoService.populaCarrinho(user);
			alterarListaBanco(idProduto, carrinho, true);
		} else {
			alterarListaSessao(idProduto, true);
		}
		return "redirect:/carrinho";
	}

	@GetMapping("/SubQuant/{idProduto}")
	public String subtrairProduto(@PathVariable int idProduto, @AuthenticationPrincipal User user) {
		if (user != null) {
			CarrinhoModel carrinho = carrinhoService.populaCarrinho(user);
			alterarListaBanco(idProduto, carrinho, false);
		} else {
			alterarListaSessao(idProduto, false);
		}
		return "redirect:/carrinho";
	}

	public boolean verificarSeExiste(ItemCarrinhoModel item, CarrinhoModel car) {
		if (car.getItens() != null) {
			for (ItemCarrinhoModel item1 : car.getItens()) {
				if (item.getProduto().getId() == item1.getProduto().getId()) {
					item1.setQuantidade(item1.getQuantidade() + item.getQuantidade());
					item1.calcularSubtotal();
					return true;
				}
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

	public CarrinhoModel alterarListaBanco(int idProduto, CarrinhoModel carrinho, boolean somar) {
		ProdutoModel produto = produtoService.getById(idProduto);
		ItemCarrinhoModel item = new ItemCarrinhoModel();
		boolean flag = false;
		if (produto.getId() != null) {
			if (carrinho != null && carrinho.getItens() != null) {
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
				carrinho.calcularTotal();
			}
			if (flag == false) {
				item.setProduto(produto);
				item.setQuantidade(1);
				item.calcularSubtotal();
				item.setCarrinho(carrinho);
				itemService.save(item);
			}

		}
		return carrinho;
	}

	@GetMapping("frete/all/{cep}")
	public String opcoesFrete(@PathVariable String cep) {
		this.carrinho.setCep(cep);
		this.fretes = this.freteService.findAll();
		for (FreteModel frete : fretes) {
			frete.setValorFrete(this.carrinho.getTotal() * frete.getTaxa());
		}
		return "redirect:/carrinho";
	}

	@GetMapping("/frete/{idFrete}")
	public String frete(@PathVariable int idFrete, @AuthenticationPrincipal User user) {
		FreteModel frete = this.freteService.findOne(idFrete);
		this.carrinho.setFrete(frete);
		this.carrinho.setValorFrete(this.carrinho.getTotal() * frete.getTaxa());
		this.carrinho.calcularCarrinho();
		if(user != null){
			carrinhoService.salvaCarrinho(user, carrinho);
		}
		return "redirect:/carrinho";
	}

	public static List<FreteModel> getFretes() {
		return fretes;
	}

	public static void setFretes(List<FreteModel> fretes) {
		CarrinhoController.fretes = fretes;
	}

	public static double getTotal() {
		return total;
	}

	public static void setTotal(double total) {
		CarrinhoController.total = total;
	}


	public List<ItemCarrinhoModel> getItens() {
		return this.itens;
	}

	public static void setItens(List<ItemCarrinhoModel> itens) {
		CarrinhoController.itens = itens;
	}

	public static CarrinhoModel getCarrinho() {
		return carrinho;
	}

	public static void setCarrinho(CarrinhoModel carrinho) {
		CarrinhoController.carrinho = carrinho;
	}
}
