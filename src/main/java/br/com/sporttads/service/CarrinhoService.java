package br.com.sporttads.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import br.com.sporttads.model.CarrinhoModel;
import br.com.sporttads.model.ClienteModel;
import br.com.sporttads.model.ItemCarrinhoModel;
import br.com.sporttads.repository.CarrinhoRepository;
import br.com.sporttads.repository.ItemCarrinhoRepository;

@Service
public class CarrinhoService {

	@Autowired
	private CarrinhoRepository carrinhoRepository;

	@Autowired
	private ItemCarrinhoRepository itemRepository;

	@Autowired
	private ClienteService clienteService;

	public CarrinhoModel salvaCarrinho(User user, CarrinhoModel carrinho) {
		if(carrinho.getCliente() == null) {
			ClienteModel clienteModel = clienteService.buscaPorEmailUser(user.getUsername());
			carrinho.setCliente(clienteModel);
		}
		return carrinhoRepository.save(carrinho);
	}

	public CarrinhoModel populaCarrinho(User user) {
		ClienteModel cliente = this.clienteService.buscaPorEmailUser(user.getUsername());
		CarrinhoModel carrinho = carrinhoRepository.findByClienteId(cliente.getId()).orElse(new CarrinhoModel());
		if(carrinho.getId() == 0){
			carrinho.setCliente(cliente);
			carrinho = carrinhoRepository.save(carrinho);
		}else{
			List<ItemCarrinhoModel> itens = itemRepository.findByCarrinhoOrderById(carrinho).orElse(new ArrayList<>());
			if (itens != null) {
				carrinho.setItens(itens);
			}
		}
		return carrinho;
	}

	public void delete(Integer id) {
		this.carrinhoRepository.deleteById(id);
	}

	public List<CarrinhoModel> findAll() {
		return this.carrinhoRepository.findAll();
	}

	public void deleteAll(){
		this.carrinhoRepository.deleteAll();
	}
}
