package br.com.sporttads.controller;

import java.util.List;

import br.com.sporttads.model.ClienteModel;
import br.com.sporttads.service.ClienteService;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.sporttads.model.EnderecoModel;
import br.com.sporttads.service.EnderecoService;

@Controller
@RequestMapping("/enderecos")
public class EnderecoController {

	@Autowired
	private EnderecoService enderecoService;

	@Autowired
	private ClienteService clienteService;

	/**
	 * Metodo usado para abrir a tela de cadastro de endereco.
	 * @return tela de cadastro.
	 */
	@GetMapping("/formulario")
	public ModelAndView getFormulario() {
		return new ModelAndView("endereco/formularioEndereco", "endereco", new EnderecoModel());
	}


	/**
	 * Metodo usado para salva um novo endereco
	 * @param endereco
	 * @param user
	 * @return tela de lista de endereços e mensagem de sucesso.
	 */
	@PostMapping("/save")
	public ModelAndView save(@ModelAttribute("endereco") EnderecoModel endereco, @AuthenticationPrincipal User user) {
		this.enderecoService.create(endereco, user);
		ModelAndView mv = getListaEndereco(user);
		mv.addObject("sucesso", "Endereço cadastrado com sucesso!");
		return mv;
	}

	/**
	 * Metodo usado para pegas endereços de entregas para o momento do checkout.
	 * @param user
	 * @return lista com endereços.
	 */
	@GetMapping("/enderecosentrega")
	public ModelAndView getEnderecoEntrega(@AuthenticationPrincipal User user) {
		List<EnderecoModel> enderecos = this.enderecoService. getByClienteId(user);
		return new ModelAndView("Pedido/EnderecoEntregaPedido", "enderecos", enderecos);
	}

	/**
	 * Metodo para abrir a lista de enderecos do usuario logado.
	 * @param user
	 * @return tela das listas de endereços.
	 */
	@GetMapping
	public ModelAndView getAllClienteId(@AuthenticationPrincipal User user) {
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
		return getListaEndereco(user);
	}

	/**
	 * Metodo para abrir a tela de alteração de endereço.
	 * @param id
	 * @return tela de alteração de endereço.
	 */
	@GetMapping("/update/{id}")
	public ModelAndView getFormularioUpdate(@PathVariable int id) {
		EnderecoModel endereco = this.enderecoService.getById(id);
		ModelAndView mv = new ModelAndView("endereco/formularioEndereco", "endereco", endereco);
		return mv;
	}

	/**
	 * Metodo para salva no banco a alteração do produto.
	 * @param enderecoEdit
	 * @param user
	 * @return tela com a lista de endereços e mensagem de sucesso da alteração.
	 */
	@PostMapping("/update")
	public ModelAndView update(@ModelAttribute("endereco") EnderecoModel enderecoEdit, @AuthenticationPrincipal User user) {
		this.enderecoService.update(enderecoEdit, user);
		ModelAndView mv = getListaEndereco(user);
		mv.addObject("sucesso", "Endereço alterado com sucesso!");
		return mv;
	}

	/**
	 * Medodo para deletar o endereço (Altera o status para inativa impossibilitando o usuario de ver o endereço novamente)
	 * @param id
	 * @param user
	 * @return Mensagem de sucesso.
	 */
	@GetMapping("delete/{id}")
	public ModelAndView delete(@PathVariable Integer id,@AuthenticationPrincipal User user) {
		this.enderecoService.delete(id);
		ModelAndView mv = getListaEndereco(user);
		mv.addObject("sucesso", "Endereço excluído com sucesso!");
		return mv;
	}

	/**
	 * Metodo usando para instancia o ModelAndView com a tela de lista de endereços com os endereços para o usuario logado.
	 * @param user
	 * @return Tela de lista de endereços
	 */
	public ModelAndView getListaEndereco(User user){
		List<EnderecoModel> enderecos = this.enderecoService.getByClienteId(user);
		return new ModelAndView("endereco/listaEnderecos", "enderecos", enderecos);
	}

}
