package br.com.sporttads.controller;

import javax.validation.Valid;

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
			attr.addFlashAttribute("falha", "CPF ESTÁ INVÁLIDO");
			return "redirect:/clientes/cadastrar";

		}
		if (service.findBycpf(cliente.getCpf()).getId() != null) {
			attr.addFlashAttribute("falha", "Este CPF já está cadastrado!!!");
			return "redirect:/clientes/cadastrar";
		}

		UsuarioModel usuario = usuarioService.findByEmail(user.getUsername());
		cliente.setUsuario(usuario);
		service.salvar(cliente);
		attr.addFlashAttribute("sucesso", "Cadastro inserido com sucesso.");
		return "redirect:/clientes/cadastrar";
	}

}
