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

	@OneToMany(mappedBy = "carrinho", fetch= FetchType.EAGER, cascade = CascadeType.ALL)
	private List<ItemCarrinhoModel> itens;

	@OneToOne
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<ItemCarrinhoModel> getItens() {
		return itens;
	}

	public ClienteModel getCliente() {
		return cliente;
	}

	public void setCliente(ClienteModel cliente) {
		this.cliente = cliente;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public int getQuantidadeTotal() {
		return quantidadeTotal;
	}

	public void setQuantidadeTotal(int quantidadeTotal) {
		this.quantidadeTotal = quantidadeTotal;
	}

	public double getValorFrete() {
		return valorFrete;
	}

	public void setValorFrete(double valorFrete) {
		this.valorFrete = valorFrete;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public double getTotalCarrinho() {
		return totalCarrinho;
	}

	public void setTotalCarrinho(double totalCarrinho) {
		this.totalCarrinho = totalCarrinho;
	}

	public FreteModel getFrete() {
		return frete;
	}

	public void setFrete(FreteModel frete) {
		this.frete = frete;
	}
}
