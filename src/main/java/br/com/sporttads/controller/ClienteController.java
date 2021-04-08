package br.com.sporttads.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import br.com.sporttads.model.ClienteModel;
import br.com.sporttads.model.UsuarioModel;
import br.com.sporttads.service.ClienteService;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

	@Autowired
	private ClienteService service;

	@GetMapping("/cadastro")
	public String cadastro() {
		return "cliente/cadastroCliente";
	}

	@PostMapping("/salvar")
	public String salvar(ClienteModel cliente, RedirectAttributes attr) {
		if (cliente.getSenha().length() < 3) {
			attr.addFlashAttribute("falha", "A senha deve ter mais de 3 caracteres");
			return "redirect:/cliente/cadastro";
		}
		service.salvar(cliente);
		attr.addFlashAttribute("sucesso", "Cadastro inserido com sucesso.");
		return "redirect:/cliente/cadastro";
	}
	
	@GetMapping("/editar/{id}")
	public String preEditar (@PathVariable("id") Long idCliente , ModelMap model ) {
		model.addAttribute("clientes", service.findById(idCliente));
		return "/cliente/cadastroCliente";
	}
	
	
	@PostMapping("/editar")
	public String editar (ClienteModel cliente, RedirectAttributes attr) {
		
		if(cliente.getNomeCompleto().length() < 3 ){
			attr.addFlashAttribute("falha", "O Nome tem que ter mais de 05 caracteres.");
			return "redirect:/cliente/cadastro";
		}else if(cliente.getSenha().length() < 3 ){
			attr.addFlashAttribute("falha", "A senha tem que ter mais de 03 caracteres.");
			return "redirect:/cliente/cadastro";
		}
		service.editar(cliente);
		attr.addFlashAttribute("sucesso", "Operação realizada com sucesso.");
	
		return "index";
		
	}
	
	
	/*
	@GetMapping(value = "/editar/{id}")
	public ResponseEntity<ClienteModel> findById(@PathVariable Long id) {
		ClienteModel obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

	@GetMapping(value="/listar")
	public ResponseEntity<List<ClienteModel>> findAll() {
		List<ClienteModel> list = service.findAll();
		return ResponseEntity.ok().body(list);

	}*/

	
}
