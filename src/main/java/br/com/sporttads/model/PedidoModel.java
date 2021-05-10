package br.com.sporttads.model;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "tb_pedido")
public class PedidoModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_pedido")
	private int id;

	@OneToMany(mappedBy = "pedido")
	private List<ItemPedidoModel> itens;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_cliente")
	private ClienteModel cliente;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_endereco")
	private EnderecoModel endereco;

	@OneToOne(mappedBy = "pedido", fetch = FetchType.EAGER)
	private CartaoModel cartao;

	private double total = 0;

	private double frete = 0;

	private String formaPagamento;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime dataHora = LocalDateTime.now();

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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<ItemPedidoModel> getItens() {
		return itens;
	}

	public ClienteModel getCliente() {
		return cliente;
	}

	public void setCliente(ClienteModel cliente) {
		this.cliente = cliente;
	}

	public EnderecoModel getEndereco() {
		return endereco;
	}

	public void setEndereco(EnderecoModel endereco) {
		this.endereco = endereco;
	}

	public CartaoModel getCartao() {
		return cartao;
	}

	public void setCartao(CartaoModel cartao) {
		this.cartao = cartao;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public String getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(String formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public LocalDateTime getDataHora() {
		return dataHora;
	}

	public void setDataHora(LocalDateTime dataHora) {
		this.dataHora = dataHora;
	}

	public int getQuantidadeTotal() {
		return quantidadeTotal;
	}

	public void setQuantidadeTotal(int quantidadeTotal) {
		this.quantidadeTotal = quantidadeTotal;
	}

	public double getFrete() {
		return frete;
	}

	public void setFrete(double frete) {
		this.frete = frete;
	}
}