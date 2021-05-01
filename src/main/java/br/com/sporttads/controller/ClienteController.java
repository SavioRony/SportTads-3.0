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

	@GetMapping("/cadastrar")
	public String cadastro(ModelMap model, @AuthenticationPrincipal User user) {
		model.addAttribute("email", user.getUsername());

		ClienteModel c = service.buscaPorEmailUser(user.getUsername());

		model.addAttribute("cliente", c);

		return "cliente/cadastroCliente";
	}

	@PostMapping("/salvar")
	public String salvar(@Valid ClienteModel cliente, BindingResult result, RedirectAttributes attr,
			@AuthenticationPrincipal User user) {

		if (result.hasErrors()) {
			attr.addFlashAttribute("falha", "CPF esta inválido");
			return "redirect:/clientes/cadastrar";
		}

		if (service.findBycpf(cliente.getCpf()).getId() != null && cliente.getId() == null) {
			attr.addFlashAttribute("falha", "Este CPF já está cadastrado!!!");
			return "redirect:/clientes/cadastrar";
		}
		if (service.validaNome(cliente.getNomeCompleto()) == false) {
			attr.addFlashAttribute("falha", "Nome invalido necessário pelo menos duas palavras!");
			return "redirect:/clientes/cadastrar";
		}

		UsuarioModel usuario = usuarioService.findByEmail(user.getUsername());
		cliente.setUsuario(usuario);
		service.salvar(cliente);

		if(enderecoService.hasEnderecoPrincipal(user)){
			attr.addFlashAttribute("sucesso", "Alterado com sucesso!");
			return "redirect:/clientes/cadastrar";
		}
		attr.addAttribute("Primeiro", "Sim");
		return "redirect:/enderecos/formulario";
	}

}
