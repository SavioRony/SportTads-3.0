package br.com.sporttads.service;

import br.com.sporttads.model.ItemCarrinhoModel;
import br.com.sporttads.repository.ItemCarrinhoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemCarrinhoService {

    @Autowired
    private ItemCarrinhoRepository itemRepository;

    public ItemCarrinhoModel save(ItemCarrinhoModel item){
        return itemRepository.save(item);
    }

    public void delete(Integer id) {
        this.itemRepository.deleteById(id);
    }
}
