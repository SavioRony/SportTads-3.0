package br.com.sporttads.model;

import javax.persistence.*;

import lombok.Data;

@Entity
@Table(name = "tb_produto")
@Data
public class ProdutoModel {

	@Id
	@Column(name = "id_produto")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(length = 200)
	private String nome;
	@Column(length = 2000)
	private String descricao;
	private Double preco;
	private Integer quantidade;
	private Integer estrelas;
	private String categoria;
	private String status;

	@Column(length = 45, nullable = true)
	private String logo;


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

	@Transient
	public String getImagemPath() {
		if (logo == null || id == null)
			return null;

		return "/imagem-principal/" + id + "/" + logo;
	}

}
