package br.com.sporttads.model;

import javax.persistence.*;

import lombok.Data;

@Entity
@Table(name = "tb_produto")
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

	@OneToOne(mappedBy = "produto")
	private ItemCarrinhoModel itemCarrinhoModel;

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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Integer getEstrelas() {
		return estrelas;
	}

	public void setEstrelas(Integer estrelas) {
		this.estrelas = estrelas;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public ItemCarrinhoModel getItemCarrinhoModel() {
		return itemCarrinhoModel;
	}

	public void setItemCarrinhoModel(ItemCarrinhoModel itemCarrinhoModel) {
		this.itemCarrinhoModel = itemCarrinhoModel;
	}
}
