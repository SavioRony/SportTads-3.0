package br.com.sporttads.service;

import br.com.sporttads.model.CartaoModel;
import br.com.sporttads.repository.CartaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartaoService {

    @Autowired
    private CartaoRepository cartaoRepository;

    public void save(CartaoModel cartao) {
        cartaoRepository.save(cartao);
    }
}
