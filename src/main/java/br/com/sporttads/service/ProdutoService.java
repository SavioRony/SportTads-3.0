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

    public ProdutoModel getById(int id) {
        return repository.findById(id);
    }

    public ProdutoModel delete(int id) {
        return repository.deleteById(id);
    }

    public ProdutoModel post(ProdutoModel produto) {
        return repository.save(produto);
    }

    public List<ProdutoModel> getAll() {
        return repository.findAll();
    }

    public ProdutoModel edit(ProdutoModel produto) {
        return repository.save(produto);
    }


}
