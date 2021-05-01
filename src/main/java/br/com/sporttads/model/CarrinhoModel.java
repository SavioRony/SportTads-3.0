package br.com.sporttads.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import java.text.DecimalFormat;
import java.util.List;

@Entity
@Table(name = "tb_carrinho")
@Data
@NoArgsConstructor
public class CarrinhoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_carrinho")
    private int id;

    @OneToMany(mappedBy = "carrinho")
    private List<ItemCarrinhoModel> itens;

    @OneToOne
    @JoinColumn(name = "id_cliente")
    private ClienteModel cliente;

    private double total = 0;

    private int quantidadeTotal = 0;

    public void calcularTotal(){
        this.total = 0;
        for (ItemCarrinhoModel itemCarrinho: this.itens) {
            this.total += itemCarrinho.getSubtotal();
        }
        quantidadeTotal = this.itens.size();
    }

}
