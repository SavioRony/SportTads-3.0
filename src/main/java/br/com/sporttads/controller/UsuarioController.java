package br.com.sporttads.controller;

import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.sporttads.model.UsuarioModel;
import br.com.sporttads.service.UsuarioService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService service;

	/**
	 * Metodo usando para abrir a tela de cadastro e alteração de usuarios.
	 * @param usuario
	 * @param model
	 * @return String - Tela de cadastro e alteração de usuarios
	 */
	@GetMapping("/cadastrar")
	public String cadastrar(UsuarioModel usuario, ModelMap model) {
		model.addAttribute("usuario", usuario);
		return "/Usuario/CadastroDeUsuario";
	}

	/**
	 * Metodo usado para abrir a tela de listagem de usuarios
	 * @return ModelAndView - Tela de listagem de usuario
	 */
	@GetMapping("/listar")
	public ModelAndView listar() {
		List<UsuarioModel> usuarios = service.findByTipoUsuario();
		return new ModelAndView("Usuario/ListaDeUsuario", "usuarios", usuarios);
	}

	/**
	 * Metodo para abrir a tela de alteração de usuario
	 * @param id
	 * @return ModelAndView - Tela de alteração de usuario
	 */
	@GetMapping("/editar/{id}")
	public ModelAndView preEditar(@PathVariable("id") Long id) {
		UsuarioModel usuario = service.findById(id);
		return new ModelAndView("Usuario/CadastroDeUsuario", "usuario", usuario);
	}

	/**
	 * Metodo para salvar os dados do usuario de alteração
	 * @param usuario
	 * @param attr
	 * @return redirect - Mensagens de validação de erro ou sucesso.
	 */
	@PostMapping("/editar")
	public String editar(UsuarioModel usuario, RedirectAttributes attr) {
		//verificar de o nome do usuario possui acima de 5 caracteres
		if (usuario.getNome().length() < 5) {
			attr.addFlashAttribute("falha", "O Nome tem que ter mais de 05 caracteres.");
			return "redirect:/usuario/cadastrar";
		//Verificar se a senha do usuario possui mais de 3 caracteres
		} else if (usuario.getSenha().length() < 3) {
			attr.addFlashAttribute("falha", "A senha tem que ter mais de 03 caracteres.");
			return "redirect:/usuario/cadastrar";
		}

		//Salva e redireciona mensagem de sucesso para tela.
		service.editar(usuario);
		attr.addFlashAttribute("sucesso", "Operação realizada com sucesso.");
		return "redirect:/usuario/cadastrar";
	}


	/**
	 * Metodo usado para salvar o usuario no banco de dados
	 * @param usuario
	 * @param attr
	 * @return String - Mensagem de validaçãoes
	 */
	@PostMapping("/salvar")
	public String salvar(UsuarioModel usuario, RedirectAttributes attr) {
		try {

			//verificar de o nome do usuario possui acima de 5 caracteres
			if (usuario.getNome().length() < 5) {
				attr.addFlashAttribute("falha", "O Nome tem que ter mais de 05 caracteres.");
				return "redirect:/usuario/cadastrar";

			//Verificar se a senha do usuario possui mais de 3 caracteres
			} else if (usuario.getSenha().length() < 3) {
				attr.addFlashAttribute("falha", "A senha tem que ter mais de 03 caracteres.");
				return "redirect:/usuario/cadastrar";
			}

			//Salvar o usuario e retorna mensagem de sucesso
			service.salvar(usuario);
			attr.addFlashAttribute("sucesso", "Operação realizada com sucesso.");

		//Caso o email ja esteja cadastrado e retorna um exception para o usuario
		} catch (DataIntegrityViolationException ex) {
			attr.addFlashAttribute("falha", "Cadastro não realizado, email já existente.");
		}
		return "redirect:/usuario/cadastrar";
	}

	/**
	 * Metodo usado para alterar o status do usuario.
	 * @param id
	 * @param attr
	 * @return String - Tela de inativar e Ativar
	 */
	@GetMapping("/inativa-ativar/{id}")
	public String inativaAtivar(@PathVariable("id") Long id, RedirectAttributes attr) {
		UsuarioModel usuario = service.findById(id);
		if (usuario.getStatus().equals("Ativo")) {
			usuario.setStatus("Inativo");
		} else {
			usuario.setStatus("Ativo");
		}
		service.inativaAtivar(usuario);
		attr.addFlashAttribute("sucesso", "Operação realizada com sucesso.");
		return "redirect:/usuario/listar";
	}

	/*
		-------------------------------------------------------------------------------------------
						Apartir daqui e referente ao usuario do tipo cliente
		-------------------------------------------------------------------------------------------
	 */

	/**
	 * Metodo para abrir a tela de cadastrar usuario cliente.
	 * @param usuario
	 * @return String - Tela de cadastra-se
	 */
	@GetMapping("/novo/cadastro")
	public String novoCadastro(UsuarioModel usuario) {
		return "cadastrar-se";
	}

	/**
	 * Metodo para salvar no banco de dados o usuario do tipo cliente com o status inativo ate a confirmação do email do cliente
	 * @param usuario
	 * @param attr
	 * @return String - Tela de mensagem de sucesso ou mensagem de falha se o email ja estiver cadastrado
	 */
	@PostMapping("/cliente/salvar")
	public String salvarCadastroCliente(UsuarioModel usuario, RedirectAttributes attr) {
		try {
			service.salvarCadastroCliente(usuario);
		} catch (DataIntegrityViolationException | MessagingException ex) {
			attr.addFlashAttribute("falha", "Ops... Este e-email já existe na base de dados.");
			return "redirect:/usuario/novo/cadastro";
		}
		return "fragments/mensagem";
	}

	/**
	 * Metodo de retorno do email de confirmação com o codigo que seria o email codificado para ativar o cadastro do usuario cliente no banco de dados.
	 * @param codigo
	 * @param attr
	 * @return String - Tela de login com a mensagem de sucesso.
	 */
	@GetMapping("/confirmacao/cadastro")
	public String respostaConfirmacaoCadastroCliente(@RequestParam("codigo") String codigo, RedirectAttributes attr) {
		service.ativarCadastroCliente(codigo);
		attr.addFlashAttribute("alerta", "sucesso");
		attr.addFlashAttribute("titulo", "Cadastro Ativado!");
		attr.addFlashAttribute("texto", "Parabéns, seu cadastro está ativo.");
		attr.addFlashAttribute("subtexto", "Siga com seu login e senha.");
		return "redirect:/login";
	}


	/**
	 * Metodo para abrir pagina de pedido de redefinição de senha com o campo de email
	 * @return String - tela de recefinir senha.
	 */
	@GetMapping("/c/redefinir-senha")
	public String pedidoRedefinirSenha() {
		return "usuario/pedido-recuperar-senha";
	}

	/**
	 * Metodo para abrir a tela de recuperação de senha
	 * @return String - Tela de recuperação de senha
	 */
	@GetMapping("/c/recuperar")
	public String recuperSenha() {
		return "Usuario/recuperar-senha";
	}


	/**
	 * Metodo para abrir o form de pedido de recupera a senha
	 * @param email
	 * @param attr
	 * @return String - url de recuperação de senha
	 * @throws MessagingException
	 */
	@GetMapping("/c/recuperar-senha")
	public String redefinirSenha(String email, RedirectAttributes attr) throws MessagingException {
		service.pedidoRedefinicaoDeSenha(email);
		attr.addFlashAttribute("sucesso",
				"Em instantes você receberá um e-email para prosseguir com a redefinição de sua senha");
		attr.addFlashAttribute("email", email);
		return "redirect:/usuario/c/recuperar";
	}


	/**
	 * Metodo para salvar a nova senha após o pedido de recuperação
	 * @param usuario
	 * @param attr
	 * @param model
	 * @return String - Tela de login se deu certo ou para tela de recuperação se estiver codigo verificador errado.
	 */
	@PostMapping("/c/nova-senha")
	public String confirmacaoDeRedefinicaoDeSenha(UsuarioModel usuario, RedirectAttributes attr, ModelMap model) {
		UsuarioModel u = service.findByEmail(usuario.getEmail());
		//Verifica se o codigo verificador estar invalido e retorna mensagem de erro
		if (!usuario.getCodigoVerificador().equals(u.getCodigoVerificador())) {
			attr.addFlashAttribute("email", usuario.getEmail());
			attr.addFlashAttribute("falha", "Codigo verificador não confere.");
			return "redirect:/usuario/c/recuperar";
		}
		//Salva o codigo como null e abre a tela de login com a mensagem de sucesso.
		u.setCodigoVerificador(null);
		service.alterarSenha(u, usuario.getSenha());

		model.addAttribute("alerta", "sucesso");
		model.addAttribute("titulo", "Senha redefinida!");
		model.addAttribute("texto", "Você já pode logar no sistema.");
		return "login";
	}

	/**
	 * Metedo para abrir a tela de editar a senha com o usuario ja logado.
	 * @return String - tela de editar senha
	 */
	@GetMapping("/editar-senha")
	public String abrirEditarSenha() {
		return "usuario/editar-senha";
	}

	/**
	 * Metodo para salvar a nova senha do cliente
	 * @param s1
	 * @param s2
	 * @param s3
	 * @param user
	 * @param attr
	 * @return String - Tela de editar senha com mensagem de sucesso ou erro.
	 */
	@PostMapping("/confirmar-senha")
	public String editarSenha(@RequestParam("senha1") String s1, @RequestParam("senha2") String s2,
			@RequestParam("senha3") String s3, @AuthenticationPrincipal User user, RedirectAttributes attr) {
		if (!s1.equals(s2)) {
			attr.addFlashAttribute("falha", "Senhas não conferem, tente  novamente");
			return "redirect:/usuario/editar-senha";
		}
		UsuarioModel u = service.findByEmail(user.getUsername());
		if (!UsuarioService.isSenhaCorreta(s3, u.getSenha())) {
			attr.addFlashAttribute("falha", "Senhas atual não confere, tente novamente.");
			return "redirect:/usuario/editar-senha";
		}
		service.alterarSenha(u, s1);
		attr.addFlashAttribute("sucesso", "Senha alterada com sucesso.");
		return "redirect:/usuario/editar-senha";
	}
}
