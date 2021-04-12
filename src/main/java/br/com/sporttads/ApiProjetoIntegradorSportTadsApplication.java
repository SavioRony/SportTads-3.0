package br.com.sporttads;

import br.com.sporttads.model.ClienteModel;
import br.com.sporttads.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EntityScan(basePackages = "br.com.sporttads.model")
@ComponentScan(basePackages = { "br.*" })
@EnableJpaRepositories(basePackages = { "br.*" })
@EnableTransactionManagement
public class ApiProjetoIntegradorSportTadsApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ApiProjetoIntegradorSportTadsApplication.class, args);
		System.out.println("API RODANDO!");
		//System.out.println("Senha 123456: " + new BCryptPasswordEncoder().encode("123456"));

	
			
	}
	// -------- Testa se o Email esta funcionando ------
	@Autowired
	EmailService service;

	@Override
	public void run(String... args) throws Exception {
		//service.enviarPedidoDeConfirmacaoDeCadastro("sporttads@gmail.com","9852pol");
	}
}
