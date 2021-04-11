package br.com.sporttads.controller;

import br.com.sporttads.model.UsuarioModel;
import br.com.sporttads.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import java.util.List;


@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService service;

	@GetMapping("/cadastrar")
	public String cadastrar(UsuarioModel usuario, ModelMap model) {
		model.addAttribute("usuario", usuario);
		return "/Usuario/CadastroDeUsuario";
	}
	
	@GetMapping("/listar")
	public ModelAndView listar() {
		List<UsuarioModel> usuarios = service.findAll();
		return new ModelAndView("Usuario/ListaDeUsuario", "usuarios",usuarios);
	}

	@GetMapping("/editar/{id}")
	public ModelAndView preEditar(@PathVariable("id") Long id, RedirectAttributes attr, ModelMap model) {
		UsuarioModel usuario = service.findById(id);
		model.addAttribute("usuario", usuario);
		return new ModelAndView("Usuario/CadastroDeUsuario", "usuario", usuario);
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


	@GetMapping("/novo/cadastro")
	public String novoCadastro(UsuarioModel usuario){
		return "cadastrar-se";
	}

	@GetMapping("/cadastro/realizado")
	public String cadastroRealizado(){

		return "fragments/mensagem";
	}

	// receber o form da pagina cadastra-se
	@PostMapping("/cliente/salvar")
	public String salvarCadastroCliente(UsuarioModel usuario, RedirectAttributes attr){
		try{
			service.salvarCadastroCliente(usuario);
		}catch (DataIntegrityViolationException | MessagingException ex){
			attr.addFlashAttribute("falha","Ops... Este e-email já existe na base de dados.");
			return "redirect:/usuario/novo/cadastro";
		}
		return "redirect:/usuario/cadastro/realizado";
	}


	@GetMapping("/confirmacao/cadastro")
	public String respostaConfirmacaoCadastroCliente(@RequestParam("codigo") String codigo, RedirectAttributes attr){
		service.ativarCadastroCliente(codigo);
		attr.addFlashAttribute("alerta","sucesso");
		attr.addFlashAttribute("titulo", "Cadastro Ativado!");
		attr.addFlashAttribute("texto", "Parabéns, seu cadastro está ativo.");
		attr.addFlashAttribute("subtexto", "Siga com seu login e senha.");
		return "redirect:/login";
	}

	// Abre a pagina de pedido de redefinição de senha
	@GetMapping("/c/redefinir-senha")
	public String pedidoRedefinirSenha(){

		return "usuario/pedido-recuperar-senha";
	}

	@GetMapping("/c/recuperar")
	public String recuperSenha(){

		return "Usuario/recuperar-senha";
	}

	// form de pedido de recuper senha
	@GetMapping("/c/recuperar-senha")
	public String redefinirSenha(String email, RedirectAttributes attr) throws MessagingException {
		service.pedidoRedefinicaoDeSenha(email);
		attr.addFlashAttribute("sucesso", "Em instantes você receberá um e-email para prosseguir com a redefinição de sua senha");
		attr.addFlashAttribute("email", email);
		return "redirect:/usuario/c/recuperar";
	}

	// salvar a nova senha via recuperacao de senha
	@PostMapping("/c/nova-senha")
	public String confirmacaoDeRedefinicaoDeSenha(UsuarioModel usuario, RedirectAttributes attr, ModelMap model){
		UsuarioModel u = service.findByEmail(usuario.getEmail());
		if(!usuario.getCodigoVerificador().equals(u.getCodigoVerificador())){
			attr.addFlashAttribute("email", usuario.getEmail());
			attr.addFlashAttribute("falha", "Codigo verificador não confere.");
			return "redirect:/usuario/c/recuperar";
		}
		u.setCodigoVerificador(null);
		service.alterarSenha(u, usuario.getSenha());

		model.addAttribute("alerta", "sucesso");
		model.addAttribute("titulo", "Senha redefinida!");
		model.addAttribute("texto", "Você já pode logar no sistema.");
		return "login";
	}

	@GetMapping("/editar-senha")
	public String abrirEditarSenha() {
		return "usuario/editar-senha";
	}

	@PostMapping("/confirmar-senha")
	public String editarSenha(@RequestParam("senha1") String s1, @RequestParam("senha2") String s2,
							  @RequestParam("senha3") String s3, @AuthenticationPrincipal User user,
							  RedirectAttributes attr){
		if(!s1.equals(s2)){
			attr.addFlashAttribute("falha", "Senhas não conferem, tente  novamente");
			return "redirect:/usuario/editar-senha";
		}
		UsuarioModel u = service.findByEmail(user.getUsername());
		if(!UsuarioService.isSenhaCorreta(s3, u.getSenha())){
			attr.addFlashAttribute("falha", "Senhas atual não confere, tente novamente.");
			return "redirect:/usuario/editar-senha";
		}
		service.alterarSenha(u, s1);
		attr.addFlashAttribute("sucesso", "Senha alterada com sucesso.");
		return "redirect:/usuario/editar-senha";
	}
}
