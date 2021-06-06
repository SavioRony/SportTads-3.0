package br.com.sporttads.controller;

import javax.validation.Valid;

import br.com.sporttads.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import br.com.sporttads.model.ClienteModel;
import br.com.sporttads.model.UsuarioModel;
import br.com.sporttads.service.ClienteService;
import br.com.sporttads.service.UsuarioService;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
	private ClienteService service;

	@Autowired
	private EnderecoService enderecoService;

	@Autowired
	private UsuarioService usuarioService;

	/**
	 * Abrir formulario de cadastro de produto ja contendo a informação do email,
	 * se caso ja possui cliente cadastrado e retornado insformaçãoes dele se não um cliente vazio.
	 * @param user
	 * @return tela de cadastro/alteração de cliente
	 */
	@GetMapping("/cadastrar")
	public ModelAndView cadastro(@AuthenticationPrincipal User user) {
		ModelAndView mv = new ModelAndView("cliente/cadastroCliente", "email", user.getUsername());
		mv.addObject("cliente", service.buscaPorEmailUser(user.getUsername()));
		return mv;
	}

	/**
	 * Metodo usado para cadastrar ou altera no banco de dodas o cliente.
	 * @param cliente
	 * @param result
	 * @param attr
	 * @param user
	 * @return Pode retorna mensagens de validação ou se for primeiro acesso direciona para tela de cadastro de endereço.
	 */
	@PostMapping("/salvar")
	public String salvar(@Valid ClienteModel cliente, BindingResult result, RedirectAttributes attr,
			@AuthenticationPrincipal User user) {

		//Valida se o CPF é valido.
		if (result.hasErrors()) {
			attr.addFlashAttribute("falha", "CPF esta inválido");
			return "redirect:/clientes/cadastrar";
		}

		//Busca no banco se o email ja foi cadastrado.
		if (service.findBycpf(cliente.getCpf()).getId() != null && cliente.getId() == null) {
			attr.addFlashAttribute("falha", "Este CPF já está cadastrado!!!");
			return "redirect:/clientes/cadastrar";
		}

		//Valida se o nome do cliente possui duas palavra e se cada e maior ou igual a 3 caracteres.
		if (service.validaNome(cliente.getNomeCompleto()) == false) {
			attr.addFlashAttribute("falha", "Nome invalido necessário pelo menos duas palavras!");
			return "redirect:/clientes/cadastrar";
		}

		//Passa o usuario da sessão para o objeto cliente.
		UsuarioModel usuario = usuarioService.findByEmail(user.getUsername());
		cliente.setUsuario(usuario);
		service.salvar(cliente);

		//Caso ja possua endereço principal so retorna a mensagem de sucesso, se não abre a tela de cadastro de endereço
		if(enderecoService.hasEnderecoPrincipal(user)){
			attr.addFlashAttribute("sucesso", "Alterado com sucesso!");
			return "redirect:/clientes/cadastrar";
		}

		return "redirect:/enderecos/formulario";
	}

}
