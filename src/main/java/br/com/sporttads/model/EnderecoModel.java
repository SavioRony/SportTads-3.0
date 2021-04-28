package br.com.sporttads.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "tb_endereco")
@NoArgsConstructor
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

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "id_cliente")
	private ClienteModel cliente;

	public EnderecoModel(String nome, String cep, String numero, String logradouro, String localidade, String bairro,
			String uf, String complemento) {
		this.nome = nome;
		this.cep = cep;
		this.numero = numero;
		this.logradouro = logradouro;
		this.localidade = localidade;
		this.bairro = bairro;
		this.uf = uf;
		this.complemento = complemento;
	}

}
