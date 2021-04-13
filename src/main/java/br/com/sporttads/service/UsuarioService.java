package br.com.sporttads.service;

import java.util.List;
import java.util.Optional;

import br.com.sporttads.exception.AcessoNegadoException;
import br.com.sporttads.model.UsuarioTipo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.RandomStringUtils;

import br.com.sporttads.model.UsuarioModel;
import br.com.sporttads.repository.UsuarioRepository;
import org.springframework.util.Base64Utils;

import javax.mail.MessagingException;

@Service
@Transactional(readOnly = false)
public class UsuarioService implements UserDetailsService {

	@Autowired
	private UsuarioRepository repository;
	@Autowired
	private EmailService emailService;

	public void salvar(UsuarioModel usuario) {
		String crypt = new BCryptPasswordEncoder().encode(usuario.getSenha());
		usuario.setSenha(crypt);
		repository.save(usuario);
	}

	public void editar(UsuarioModel usuario) {
		String crypt = new BCryptPasswordEncoder().encode(usuario.getSenha());
		usuario.setSenha(crypt);
		repository.save(usuario);

	}

	public void inativaAtivar(UsuarioModel usuario) {
		repository.save(usuario);
	}

	@Transactional(readOnly = true)
	public UsuarioModel findById(Long id) {
		return repository.findById(id).get();

	}

	@Transactional(readOnly = true)
	public List<UsuarioModel> findAll() {

		return repository.findAll();
	}

	@Transactional(readOnly = true)
	public UsuarioModel findByEmail(String email) {
		return repository.findByEmail(email);
	}

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UsuarioModel usuario = buscarPorEmailEAtivo(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuario " + username + " não encontrado."));
		if (usuario.getStatus().equals("Inativo")) {
			throw new UsernameNotFoundException("Usuario Inativo");
		}
		return new User(usuario.getEmail(), usuario.getSenha(), AuthorityUtils.createAuthorityList(usuario.getTipo()));
	}

	@Transactional(readOnly = false)
	public void salvarCadastroCliente(UsuarioModel usuario) throws MessagingException {
		String cript = new BCryptPasswordEncoder().encode(usuario.getSenha());
		usuario.setSenha(cript);
		usuario.setTipo(UsuarioTipo.CLIENTE.getDesc());
		usuario.setStatus("Inativo");
		repository.save(usuario);

		emailDeConfirmacaoDeCadastro(usuario.getEmail());
	}

	@Transactional(readOnly = true)
	public Optional<UsuarioModel> buscarPorEmailEAtivo(String email) {
		return repository.findByEmailEAtivo(email);
	}

	public void emailDeConfirmacaoDeCadastro(String email) throws MessagingException {
		String codigo = Base64Utils.encodeToString(email.getBytes());
		emailService.enviarPedidoDeConfirmacaoDeCadastro(email, codigo);
	}

	@Transactional(readOnly = false)
	public void ativarCadastroCliente(String codigo) {
		String email = new String(Base64Utils.decodeFromString(codigo));
		UsuarioModel usuario = findByEmail(email);
		if (usuario == null) {
			throw new AcessoNegadoException("Não foi possivel ativar ser cadastro. Entre em contato com o suporte");
		}
		usuario.setStatus("Ativo");
	}

	@Transactional(readOnly = false)
	public void pedidoRedefinicaoDeSenha(String email) throws MessagingException {
		UsuarioModel usuario = buscarPorEmailEAtivo(email)
				.orElseThrow(() -> new UsernameNotFoundException("Usuario " + email + " não encontrado."));

		String verificador = RandomStringUtils.randomAlphanumeric(6);

		usuario.setCodigoVerificador(verificador);

		emailService.enviarPedidoRedefinirSenha(email, verificador);
	}

	@Transactional(readOnly = false)
	public void alterarSenha(UsuarioModel usuario, String senha) {
		usuario.setSenha(new BCryptPasswordEncoder().encode(senha));
		repository.save(usuario);
	}

	public static boolean isSenhaCorreta(String senhaDigitada, String senhaArmazenada) {
		return new BCryptPasswordEncoder().matches(senhaDigitada, senhaArmazenada);
	}
}
