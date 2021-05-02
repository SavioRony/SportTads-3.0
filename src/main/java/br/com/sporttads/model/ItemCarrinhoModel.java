package br.com.sporttads.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_item_carrinho")
@Data
@NoArgsConstructor
public class ItemCarrinhoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_item_carrinho")
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_carrinho")
    private CarrinhoModel carrinho;

    @OneToOne
    @JoinColumn(name = "id_produto")
    private ProdutoModel produto;

    private int quantidade;

    private double subtotal;

    public void calcularSubtotal(){
        this.subtotal = quantidade * produto.getPreco();
    }
}
