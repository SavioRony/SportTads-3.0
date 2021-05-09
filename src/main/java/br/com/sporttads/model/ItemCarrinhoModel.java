package br.com.sporttads.model;

import javax.persistence.*;


@Entity
@Table(name = "tb_item_carrinho")
public class ItemCarrinhoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_item_carrinho")
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_carrinho")
    private CarrinhoModel carrinho;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_produto")
    private ProdutoModel produto;

    private int quantidade;

    private double subtotal;

    public void calcularSubtotal(){
        this.subtotal = quantidade * produto.getPreco();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CarrinhoModel getCarrinho() {
        return carrinho;
    }

    public void setCarrinho(CarrinhoModel carrinho) {
        this.carrinho = carrinho;
    }

    public ProdutoModel getProduto() {
        return produto;
    }

    public void setProduto(ProdutoModel produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
}
