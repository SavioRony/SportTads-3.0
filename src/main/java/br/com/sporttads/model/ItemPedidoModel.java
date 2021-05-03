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
@Table(name = "tb_item_pedido")
@Data
@NoArgsConstructor
public class ItemPedidoModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_item_pedido")
	private int id;

	@ManyToOne
	@JoinColumn(name = "id_pedido")
	private PedidoModel pedido;

	@OneToOne
	@JoinColumn(name = "id_produto")
	private ProdutoModel produto;

	private int quantidade;

	private double subtotal;

	public void calcularSubtotal() {
		this.subtotal = quantidade * produto.getPreco();
	}

}