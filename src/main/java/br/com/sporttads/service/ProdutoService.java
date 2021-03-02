package br.com.sporttads.service;

import br.com.sporttads.model.ProdutoModel;
import br.com.sporttads.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    public ProdutoModel getById(Integer id) {
        return repository.findById(id).orElse(new ProdutoModel());
    }

     public void delete(Integer id) {
        repository.deleteById(id);
    }

    public ProdutoModel post(ProdutoModel produto) {
        return repository.save(produto);
    }

    public List<ProdutoModel> getAll() {
        return repository.findAll();
    }

        public ProdutoModel edit(Integer id) {
        ProdutoModel produtoModel = getById(id);
        return repository.save(produtoModel);
    }


}
