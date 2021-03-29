package br.com.sporttads.controller;

import br.com.sporttads.model.UsuarioModel;
import br.com.sporttads.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService service;

	@GetMapping("/cadastrar")
	public String cadastrar(UsuarioModel usuario, ModelMap model) {
		model.addAttribute("usuario", usuario);
		return "/Usuario/cadastroUsuario";
	}
	
	@GetMapping("/listar")
	public ModelAndView listar() {
		List<UsuarioModel> usuarios = service.findAll();
		return new ModelAndView("Usuario/listaUsuarios", "usuarios",usuarios);
	}

	@GetMapping("/editar/{id}")
	public ModelAndView preEditar(@PathVariable("id") Long id, RedirectAttributes attr, ModelMap model) {
		UsuarioModel usuario = service.findById(id);
		model.addAttribute("usuario", usuario);
		return new ModelAndView("Usuario/cadastroUsuario", "usuario", usuario);
	}

	@PostMapping("/editar")
	public String editar(UsuarioModel usuario, RedirectAttributes attr) {
		if(usuario.getNome().length() < 3 ){
			attr.addFlashAttribute("falha", "O Nome tem que ter mais de 05 caracteres.");
			return "redirect:/usuario/cadastrar";
		}else if(usuario.getSenha().length() < 3 ){
			attr.addFlashAttribute("falha", "A senha tem que ter mais de 03 caracteres.");
			return "redirect:/usuario/cadastrar";
		}
		service.editar(usuario);
		attr.addFlashAttribute("sucesso", "Operação realizada com sucesso.");
		return "redirect:/usuario/cadastrar";
	}

	@PostMapping("/salvar")
	public String salvar(UsuarioModel usuario, RedirectAttributes attr) {
		try {
			if(usuario.getNome().length() < 3 ){
				attr.addFlashAttribute("falha", "O Nome tem que ter mais de 05 caracteres.");
				return "redirect:/usuario/cadastrar";
			}else if(usuario.getSenha().length() < 3 ){
				attr.addFlashAttribute("falha", "A senha tem que ter mais de 03 caracteres.");
				return "redirect:/usuario/cadastrar";
			}
			service.salvar(usuario);
			attr.addFlashAttribute("sucesso", "Operação realizada com sucesso.");
		} catch (DataIntegrityViolationException ex) {
			attr.addFlashAttribute("falha", "Cadastro não realizado, email já existente.");
		}
		return "redirect:/usuario/cadastrar";
	}

	@GetMapping("/inativaAtivar/{id}")
	public String inativaAtivar(@PathVariable("id") Long id, RedirectAttributes attr ) {
		UsuarioModel usuario = service.findById(id);
		if(usuario.getStatus().equals("Ativo")){
			usuario.setStatus("Inativo");
		}else{
			usuario.setStatus("Ativo");
		}
		service.inativaAtivar(usuario);
		attr.addFlashAttribute("sucesso", "Operação realizada com sucesso.");
		return "redirect:/usuario/listar";
	}

}
