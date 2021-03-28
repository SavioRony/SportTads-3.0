package br.com.sporttads.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	
	@GetMapping("/cadastrar")
	public String cadastrar() {
		return "/Usuario/cadastroUsuario";
		
	}
	
	@GetMapping("/listar")
	public String listar() {
		return "/Usuario/listaUsuario";
	}
	
	@GetMapping("/editar")
	public String editar() {
		return "/Usuario/editarUsuario";
	}
		
}
