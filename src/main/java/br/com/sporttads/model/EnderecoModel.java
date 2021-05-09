package br.com.sporttads.model;

import javax.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name = "tb_endereco")
public class EnderecoModel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_endereco")
	private Integer id;

	private String nome;

	private String cep;

	private String numero;

	private String logradouro;

	private String localidade;

	private String bairro;

	private String uf;

	private String complemento;

	private String status;

	private Integer tipoEndereco;

	private Boolean isPadrao;

	@OneToOne(mappedBy = "endereco")
	private PedidoModel pedido;

	@ManyToOne
	@JoinColumn(name = "id_cliente")
	private ClienteModel cliente;

	public EnderecoModel() {
		this.status = "Ativo";
	}

	public EnderecoModel(String nome, String cep, String numero, String logradouro, String localidade, String bairro,
			String uf, String complemento, Integer tipoEndereco, Boolean isPadrao) {
		this.nome = nome;
		this.cep = cep;
		this.numero = numero;
		this.logradouro = logradouro;
		this.localidade = localidade;
		this.bairro = bairro;
		this.uf = uf;
		this.complemento = complemento;
		this.status = "Ativo";
		this.tipoEndereco = tipoEndereco;
		this.isPadrao = isPadrao;
	}

}
