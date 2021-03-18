package br.com.sporttads.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

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
	
	@Column(length = 45, nullable = true)
	private String logo;
		
	
//	@OneToMany(targetEntity = ImagemModel.class)
//	private List<ImagemModel> imagens;

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
	
	@Transient
	public String getImagemPath() {
		if(logo == null || id == null) return null;
		
		return "/imagem-principal/" + id + "/" + logo;
	}

	
//	public List<ImagemModel> getImagens() {
//		return imagens;
//	}
//
//	public void setImagens(List<ImagemModel> imagens) {
//		this.imagens = imagens;
//	}
	

}
