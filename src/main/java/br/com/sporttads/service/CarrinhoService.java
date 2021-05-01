package br.com.sporttads.service;

import br.com.sporttads.model.CarrinhoModel;
import br.com.sporttads.model.ClienteModel;
import br.com.sporttads.model.ItemCarrinhoModel;
import br.com.sporttads.repository.CarrinhoRepository;
import br.com.sporttads.repository.ItemCarrinhoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarrinhoService {

    @Autowired
    private CarrinhoRepository carrinhoRepository;
    @Autowired
    private ItemCarrinhoRepository itemRepository;
    @Autowired
    private ClienteService clienteService;

    public void salvaCarrinho(User user, CarrinhoModel carrinhoModel){
        if(user != null){
            ClienteModel clienteModel = clienteService.buscaPorEmailUser(user.getUsername());
            carrinhoModel.setCliente(clienteModel);
            CarrinhoModel carrinho = carrinhoRepository.save(carrinhoModel);

            for(ItemCarrinhoModel item : carrinhoModel.getItens()){
                item.setCarrinho(carrinho);
                itemRepository.save(item);
            }
        }
    }
}
