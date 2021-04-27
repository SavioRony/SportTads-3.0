package br.com.sporttads.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.sporttads.model.UsuarioTipo;
import br.com.sporttads.service.UsuarioService;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String ADMIN = UsuarioTipo.Administrador.getDesc();
	private static final String ESTOQUISTA = UsuarioTipo.Estoquista.getDesc();
	private static final String CLIENTE = UsuarioTipo.CLIENTE.getDesc();

	@Autowired
	private UsuarioService service;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
				// acessos públicos liberados
				.antMatchers("/webjars/**", "/css/**", "/img/**", "/materialize/**", "/Dashboard/**").permitAll()
				.antMatchers("/").permitAll().antMatchers("/pesquisar-por-nome").permitAll()
				.antMatchers("/pesquisar-por-categotia").permitAll()
				.antMatchers("/usuario/novo/cadastro", "/usuario/cadastro/realizado", "/usuario/cliente/salvar")
				.permitAll().antMatchers("/usuario/confirmacao/cadastro").permitAll().antMatchers("/usuario/c/**")
				.permitAll()

				// Permissões para imagens dos produtos
				.antMatchers("/imagem-principal/**").permitAll().antMatchers("/imagens-produto/**").permitAll()
				.antMatchers("/Dashboard/**").permitAll()

				// acessos ao usuario
				.antMatchers("/usuario/editar-senha", "/usuario/confirmar-senha").hasAuthority(CLIENTE)
				.antMatchers("/usuario/listar").hasAnyAuthority(ADMIN, ESTOQUISTA).antMatchers("/usuario/**")
				.hasAuthority(ADMIN)

				.antMatchers("/clientes/cadastrar").hasAuthority(CLIENTE)

				// acessos endereço
				.antMatchers("/enderecos/**", "/enderecos/save").hasAuthority(CLIENTE)

				// acessos ao produtos

				.antMatchers("/produtos/comprar-produto/*").permitAll().antMatchers("/produtos/**")
				.hasAnyAuthority(ADMIN, ESTOQUISTA)

				.anyRequest().authenticated().and().formLogin().loginPage("/login").defaultSuccessUrl("/", true)
				.failureUrl("/login-error").permitAll().and().logout().logoutSuccessUrl("/").and().exceptionHandling()
				.accessDeniedPage("/acesso-negado").and().rememberMe();

	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(service).passwordEncoder(new BCryptPasswordEncoder());
	}

}
