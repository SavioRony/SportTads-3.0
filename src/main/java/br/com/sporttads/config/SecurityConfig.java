package br.com.sporttads.config;

import br.com.sporttads.model.UsuarioTipo;
import br.com.sporttads.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String ADMIN = UsuarioTipo.Administrador.getDesc();
    private static final String ESTOQUISTA = UsuarioTipo.Estoquista.getDesc();


	@Autowired
	private UsuarioService service;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
			// acessos públicos liberados
			.antMatchers( "/webjars/**", "/css/**", "/img/**", "/materialize/**", "/Dashboard/**").permitAll()
			.antMatchers("/").permitAll()
			.antMatchers("/pesquisar-por-nome").permitAll()
				.antMatchers("/pesquisar-por-categotia").permitAll()

			//Permissões para imagens dos produtos
			.antMatchers("/imagem-principal/**").permitAll()
			.antMatchers("/imagens-produto/**").permitAll()
			.antMatchers("/Dashboard/**").permitAll()

			// acessos ao usuario
			.antMatchers("/usuario/listar").hasAnyAuthority(ADMIN,ESTOQUISTA)
			.antMatchers("/home").hasAnyAuthority(ADMIN,ESTOQUISTA)
			.antMatchers("/usuario/**").hasAnyAuthority(ADMIN)

			// acessos ao produtos
			.antMatchers("/produtos/comprar-produto/*").permitAll()
			.antMatchers("/produtos/**").hasAnyAuthority(ADMIN,ESTOQUISTA)


				.anyRequest().authenticated()
			.and()
				.formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/home", true)
				.failureUrl("/login-error")
				.permitAll()
			.and()
				.logout()
				.logoutSuccessUrl("/login")
			.and()
				.exceptionHandling()
				.accessDeniedPage("/acesso-negado")

			//Testar
			.and()
				.formLogin()
				.loginPage("/login-cliente")
				.defaultSuccessUrl("/index", true)
				.failureUrl("/login-error-cliente")
				.permitAll()
				.and()
				.logout()
				.logoutSuccessUrl("/login-cliente")
				.and()
				.exceptionHandling()
				.accessDeniedPage("/acesso-negado-cliente");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(service).passwordEncoder(new BCryptPasswordEncoder());
	}

}
