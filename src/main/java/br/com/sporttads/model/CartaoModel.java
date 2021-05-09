package br.com.sporttads.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_cartao")
public class CartaoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int numero;
    private String nome;
    private int cvv;
    private int parcelas;
    private String validade;
    private String formaPagamento;

    @OneToOne
    @JoinColumn(name = "id_pedido")
    private PedidoModel pedido;
}
