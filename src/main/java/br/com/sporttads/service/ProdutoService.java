package br.com.sporttads.service;

import br.com.sporttads.model.ProdutoModel;
import br.com.sporttads.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repostirory;

    public ProdutoModel getById(Integer id) {
        return repostirory.findById(id);
    }

    public ProdutoModel delete(Integer id) {
        return repostirory.deleteById(id);
    }

    public ProdutoModel post(ProdutoModel produto) {
        return repostirory.save(produto);
    }

    public List<ProdutoModel> getAll() {
        return repostirory.findAll();
    }

    public ProdutoModel edit(ProdutoModel produto) {
        return repostirory.save(produto);
    }


}
