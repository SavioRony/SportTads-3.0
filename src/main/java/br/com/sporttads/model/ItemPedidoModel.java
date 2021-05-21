package br.com.sporttads.model;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_item_pedido")
@Data
@NoArgsConstructor
public class ItemPedidoModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_item_pedido")
	private int id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_pedido")
	private PedidoModel pedido;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_produto")
	private ProdutoModel produto;

	@Column
	private int quantidade;

	@Column
	private double valorUnitario;

	@Column
	private double subtotal;

	public void calcularSubtotal() {
		this.subtotal = quantidade * produto.getPreco();
	}

}