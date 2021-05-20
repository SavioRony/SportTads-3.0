package br.com.sporttads.model;

import java.util.List;

import javax.persistence.*;

import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_carrinho")
@NoArgsConstructor
public class CarrinhoModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_carrinho")
	private int id;

	@OneToMany(mappedBy = "carrinho", cascade = CascadeType.ALL)
	private List<ItemCarrinhoModel> itens;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_cliente")
	private ClienteModel cliente;

	private double total = 0;

	private int quantidadeTotal = 0;

	private double valorFrete;

	@Transient
	private String cep = "";

	private double totalCarrinho;

	@OneToOne
	@JoinColumn(name = "id_frete")
	private FreteModel frete;

	public void calcularTotal() {
		this.total = 0;
		for (ItemCarrinhoModel itemCarrinho : this.itens) {
			this.total += itemCarrinho.getSubtotal();
		}
		quantidadeTotal = this.itens.size();
	}

  public void setItens(List<ItemCarrinhoModel> itens) {
      this.itens = itens;
  }

  public void calcularCarrinho() {
      this.totalCarrinho = this.total + this.valorFrete;
  }

}
