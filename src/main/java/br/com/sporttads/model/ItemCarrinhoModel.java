package br.com.sporttads.model;

import lombok.Data;

import javax.persistence.*;


@Entity
@Table(name = "tb_item_carrinho")
@Data
public class ItemCarrinhoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_item_carrinho")
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_carrinho")
    private CarrinhoModel carrinho;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_produto")
    private ProdutoModel produto;

    private int quantidade;

    private double subtotal;

    public void calcularSubtotal() {
        this.subtotal = quantidade * produto.getPreco();
    }

}
