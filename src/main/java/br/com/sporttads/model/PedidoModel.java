package br.com.sporttads.model;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "tb_pedido")
@NoArgsConstructor
public class PedidoModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_pedido")
	private int id;

	@OneToMany(mappedBy = "pedido")
	private List<ItemPedidoModel> itens;

	@OneToOne
	@JoinColumn(name = "id_cliente")
	private ClienteModel cliente;

	private double total = 0;

	private int quantidadeTotal = 0;

	public void calcularTotal() {
		this.total = 0;
		for (ItemPedidoModel item : this.itens) {
			this.total += item.getSubtotal();
		}
		quantidadeTotal = this.itens.size();
	}

	public void setItens(List<ItemPedidoModel> itens) {
		this.itens = itens;
	}
}