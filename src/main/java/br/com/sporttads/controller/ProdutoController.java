package br.com.sporttads.controller;

import br.com.sporttads.model.ProdutoModel;
import br.com.sporttads.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/{id}")
    public ResponseEntity getById(Integer id) {
        return new ResponseEntity(produtoService.getById(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity getAll() {
        return new ResponseEntity(produtoService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity post(@RequestBody ProdutoModel produto) {
        return new ResponseEntity(produtoService.post(produto), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity edit(@RequestBody ProdutoModel produto) {
        return new ResponseEntity(produtoService.edit(produto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(Integer id) {
        return new ResponseEntity(produtoService.delete(id), HttpStatus.OK);
    }
}

