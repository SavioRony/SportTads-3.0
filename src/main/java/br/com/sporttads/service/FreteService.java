package br.com.sporttads.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.sporttads.model.FreteModel;
import br.com.sporttads.repository.FreteRepository;

@Service
public class FreteService {

	@Autowired
	private FreteRepository repository;

	public FreteModel findOne(int idFrete) {
		Optional<FreteModel> frete = this.repository.findById(idFrete);
		return frete.get();
	}

	public List<FreteModel> findAll() {
		return this.repository.findAll();
	}

}
