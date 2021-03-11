package br.com.sporttads.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "tb_produto")
@Data
public class ProdutoModel {

	@Id
	@Column(name = "idProduto")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String nome;
	private String descricao;
	private Double preco;
	private Integer quantidade;
	private Integer estrelas;
	private String categoria;
	private String status;

	@OneToMany(targetEntity = ImagemModel.class)
	private List<ImagemModel> imagens;

	public ProdutoModel(Integer id, String nome, String descricao, Double preco, Integer quantidade, Integer estrelas,
			String categoria, String status) {
		super();
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.preco = preco;
		this.quantidade = quantidade;
		this.estrelas = estrelas;
		this.categoria = categoria;
		this.status = status;
	}

	public ProdutoModel() {
		this.status = "Ativo";
	}

}
