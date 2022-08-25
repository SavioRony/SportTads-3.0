package br.com.sporttads.controller;

import java.util.ArrayList;
import java.util.List;

import br.com.sporttads.model.*;
import br.com.sporttads.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
	private ClienteService clienteService;

	@Autowired
	private FreteService freteService;

	//Flag para validar o acesso após o login
	private boolean primeiroAcesso = true;

	private static CarrinhoModel carrinho = new CarrinhoModel();

	private static List<ItemCarrinhoModel> itens = new ArrayList<>();

	private static List<FreteModel> fretes = new ArrayList<>();

	private static double total;

	/**
	 * Metedo para abrir a tela do carrinho mais e feito diversas verificação com primeiro acesso
	 * para atualiza a lista do carrinho conforme os itens em sessão com os que estão salvo no banco
	 * e atualização dos valores de frete e valor total.
	 * @param user
	 * @return ModelAndView - Tela do carrinho
	 */
	@GetMapping()
	public ModelAndView mostrarTela(@AuthenticationPrincipal User user) {

		//Verifica se o cliente possui os dados cadastrados.
		if(user != null){
			ClienteModel cliente = clienteService.buscaPorEmailUser(user.getUsername());
			//Caso não tenho registro redireciona para tela de cadastro de cliente
			if(cliente.getId() == null){
				ModelAndView andView = new ModelAndView("cliente/cadastroCliente", "cliente", cliente);
				andView.addObject("email", user.getUsername());
				return andView;
			}
		}

		//Verifica se o cep do carrinho não esta vazio ou null para setar as opções de frete.
		if (this.carrinho.getCep() != null && !("".equals(this.carrinho.getCep()))) {
			this.opcoesFrete(this.carrinho.getCep());
		}

		if (user != null) {
			CarrinhoModel carrinho = carrinhoService.populaCarrinho(user);
			//Apos chama o metodo popular carrinho se volta vazio passa os itens da sessão para o objeto carrinho para salvar no banco de dados.
			if (carrinho == null) {
				if (this.carrinho.getItens() == null) {
					this.carrinho.setItens(itens);
				}
				carrinhoService.salvaCarrinho(user, this.carrinho);
			}
			/*
				Caso for o primeiro acesso e verificado todos os itens para saber se ja possui o mesmo item
				no banco de dados com os que estão na sessão e realizado ou adição do produto no banco ou
				a soma da quantidade de cada um deles para impedir produtos duplicados no carrinho.
			 */
			if (primeiroAcesso == true) {
				primeiroAcesso = false;
				if (this.carrinho != null && this.carrinho.getItens() != null) {
					if (this.carrinho.getItens().size() != 0) {
						for (ItemCarrinhoModel item : this.carrinho.getItens()) {
							//Chama o metodo para verificar se ja possui se não e registrado um novo no banco
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

						//Calculado o valor do valor frete conforme os novos produtos
						if(this.carrinho.getFrete() != null) {
							this.carrinho.setValorFrete(this.carrinho.getTotal() * this.carrinho.getFrete().getTaxa());
						}
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

		// Verificação para calcular o valor do frete baseado no tipo do frete selecionado.
		if(this.carrinho.getFrete() != null){
			this.carrinho.setValorFrete(this.carrinho.getTotal() * this.carrinho.getFrete().getTaxa());
			//Atualizar a lista de frete com os valores atualizados
			for (FreteModel frete : fretes){
				frete.setValorFrete(this.carrinho.getTotal() * frete.getTaxa());
			}
		}
		//Calcular o valor total com a soma com o frete
		this.total = this.carrinho.getTotal() + this.carrinho.getValorFrete();
		return new ModelAndView("carrinho");
	}

	/**
	 * Metedo usando para adicionar novo item no carrinho, sempre verificando se esta logado ou não para ver se preciso persistir no banco de dados ou não;
	 * @param idProduto
	 * @param user
	 * @return String - Tela de carrinho com os valores atualizados.
	 */
	@GetMapping("/adicionar/{idProduto}")
	public String addProduto(@PathVariable int idProduto, @AuthenticationPrincipal User user) {

		//Cliente logado
		if (user != null) {
			CarrinhoModel carrinho = carrinhoService.populaCarrinho(user);
			alterarListaBanco(idProduto, carrinho, true);

		//Cliente deslogado
		} else {
			alterarListaSessao(idProduto, true);
		}
		return "redirect:/carrinho";
	}

	/**
	 * Metodo para somar a quantidade de produto no carrinho.
	 * @param idProduto
	 * @param user
	 * @return String - Tela do carrinho com os dados atualizados.
	 */
	@GetMapping("/somarQuant/{idProduto}")
	public String somarProduto(@PathVariable int idProduto, @AuthenticationPrincipal User user) {

		//Cliente logado
		if (user != null) {
			CarrinhoModel carrinho = carrinhoService.populaCarrinho(user);
			alterarListaBanco(idProduto, carrinho, true);

		//Cliente deslogado
		} else {
			alterarListaSessao(idProduto, true);
		}
		return "redirect:/carrinho";
	}

	/**
	 * Metodo para subtrair quantidade de produto do carrinho.
	 * @param idProduto
	 * @param user
	 * @return String - Tela de carrinho com os dados atualizados.
	 */
	@GetMapping("/SubQuant/{idProduto}")
	public String subtrairProduto(@PathVariable int idProduto, @AuthenticationPrincipal User user) {

		//Cliente logado
		if (user != null) {
			CarrinhoModel carrinho = carrinhoService.populaCarrinho(user);
			alterarListaBanco(idProduto, carrinho, false);

		//Cliente deslogado
		} else {
			alterarListaSessao(idProduto, false);
		}
		return "redirect:/carrinho";
	}

	/**
	 * Metodo para remover produto do banco de dados ou da sessão caso cliente esteja deslogado.
	 * @param idProduto
	 * @param user
	 * @return String - Tela do carrinho com os dados atualizados.
	 */
	@GetMapping("/remover/{idProduto}")
	public String removerProduto(@PathVariable int idProduto, @AuthenticationPrincipal User user) {
		int index = getIndex(idProduto);
		if (index >= 0) {
			//Cliente logado
			if (user != null) {
				ItemCarrinhoModel itemCarrinhoModel = carrinho.getItens().get(index);
				itemService.delete(itemCarrinhoModel);
			//Cliente deslogado
			} else {
				this.itens.remove(index);
				this.carrinho.setItens(this.itens);
				this.carrinho.calcularTotal();
			}
		}
		return "redirect:/carrinho";
	}

	/**
	 * Metodo para pegar a lista de frete com os valores atualizados na sessão
	 * @param cep
	 * @return String - Tela de carrinho com a lista de fretes atualizados
	 */
	@GetMapping("frete/all/{cep}")
	public String opcoesFrete(@PathVariable String cep) {
		this.carrinho.setCep(cep);
		this.fretes = this.freteService.findAll();
		for (FreteModel frete : fretes) {
			frete.setValorFrete(this.carrinho.getTotal() * frete.getTaxa());
		}
		return "redirect:/carrinho";
	}

	/**
	 * Metodo usado para selecionar o frete e atualizar os valores da sessão.
	 * @param idFrete
	 * @param user
	 * @return String - Tela de carrinho com o frete selecionado.
	 */
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

	/**
	 * Metodo para verificar se existe item no carrinho com o item passado que veio do banco de dados
	 * para evitar duplicidades de itens no carrinho.
	 * @param item
	 * @param car
	 * @return boolean - true de existe e false se não existe.
	 */
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

	/**
	 * Metodo para pega o index do produto na lista de produtos do carrinho.
	 * @param idProduto
	 * @return int - 0 acima caso encontre, -1 caso não encontre.
	 */
	public int getIndex(int idProduto) {
		int index = -1;
		for (ItemCarrinhoModel item : carrinho.getItens()) {
			if (item.getProduto().getId() == idProduto) {
				return carrinho.getItens().indexOf(item);
			}
		}
		return index;
	}

	/**
	 * Metodo para alterar a lista da sessão para somar ou subtrair a quantidade no carrinho conforme a flag somar,
	 * caso não encontre na lista e adicionado na lista esse produto.
	 * @param idProduto
	 * @param somar
	 */
	public void alterarListaSessao(int idProduto, boolean somar) {
		ProdutoModel produto = produtoService.getById(idProduto);
		boolean existe = false;
		if (produto.getId() != null) {
			for (ItemCarrinhoModel itemCarrinho : this.itens) {
				if (itemCarrinho.getProduto().getId() == produto.getId() && somar) {
					itemCarrinho.setQuantidade(itemCarrinho.getQuantidade() + 1);
					itemCarrinho.calcularSubtotal();
					existe = true;
					break;
				}

				if (itemCarrinho.getProduto().getId() == produto.getId() && !somar) {
					if (itemCarrinho.getQuantidade() != 1) {
						itemCarrinho.setQuantidade(itemCarrinho.getQuantidade() - 1);
						itemCarrinho.calcularSubtotal();
					}
					existe = true;
					break;
				}
			}
			if (existe == false) {
				ItemCarrinhoModel item = new ItemCarrinhoModel();
				item.setProduto(produto);
				item.setQuantidade(1);
				item.calcularSubtotal();
				this.itens.add(item);
			}
			this.carrinho.setItens(this.itens);
			this.carrinho.calcularTotal();
		}
	}

	/**
	 * Metodo para alterar a lista do banco de dados para somar ou subtrair a quantidade no carrinho conforme a flag somar,
	 * caso não encontre na lista e adicionado na lista esse produto e salvando no banco de dados.
	 * @param idProduto
	 * @param carrinho
	 * @param somar
	 */
	public void alterarListaBanco(int idProduto, CarrinhoModel carrinho, boolean somar) {
		ProdutoModel produto = produtoService.getById(idProduto);
		boolean existe = false;
		if (produto.getId() != null) {
			if (carrinho != null && carrinho.getItens() != null) {
				for (ItemCarrinhoModel itemCarrinho : carrinho.getItens()) {
					if (itemCarrinho.getProduto().getId() == produto.getId() && somar) {
						itemCarrinho.setQuantidade(itemCarrinho.getQuantidade() + 1);
						itemCarrinho.calcularSubtotal();
						itemService.save(itemCarrinho);
						existe = true;
						break;
					}

					if (itemCarrinho.getProduto().getId() == produto.getId() && !somar) {
						if (itemCarrinho.getQuantidade() != 1) {
							itemCarrinho.setQuantidade(itemCarrinho.getQuantidade() - 1);
							itemCarrinho.calcularSubtotal();
							itemService.save(itemCarrinho);
						}
						existe = true;
						break;
					}

				}
				carrinho.calcularTotal();
			}
			if (existe == false) {
				ItemCarrinhoModel item = new ItemCarrinhoModel();
				item.setProduto(produto);
				item.setQuantidade(1);
				item.calcularSubtotal();
				item.setCarrinho(carrinho);
				itemService.save(item);
			}
		}
	}

	/*
		Get e Set para recuperar os itens na sessão.
	 */

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
