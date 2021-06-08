package br.com.sporttads;

import br.com.sporttads.model.FreteModel;
import br.com.sporttads.repository.FreteRepository;
import br.com.sporttads.service.FreteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
@EntityScan(basePackages = "br.com.sporttads.model")
@ComponentScan(basePackages = { "br.*" })
@EnableJpaRepositories(basePackages = { "br.*" })
@EnableTransactionManagement
public class ApiProjetoIntegradorSportTadsApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ApiProjetoIntegradorSportTadsApplication.class, args);
		System.out.println("API RODANDO!");

	}

	@Autowired
	private FreteRepository freteRepository;

	@Transactional
	@Override
	public void run(String... args) throws Exception {

		if(freteRepository.count() == 0){
			freteRepository.save(new FreteModel("Retira na loja - até 2 horas após pagamento", 0));
			freteRepository.save(new FreteModel("Normal - receba de 7 a 10 dias úteis após pagamento", 0.02));
			freteRepository.save(new FreteModel("Expressa - receba de 3 a 5 dias úteis após pagamento", 0.04));
		}
	}
}
