package br.com.sporttads.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;

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
