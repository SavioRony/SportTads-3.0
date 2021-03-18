package br.com.sporttads.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@Entity
@Table(name = "tb_imagem")
@Data
public class ImagemModel {

	@Id
	@Column(name = "idImagem")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String caminho;
	
//	@Column(length = 45, nullable = true)
//	private String logo;
	
	@Column
	private Integer idProduto;

//	@ManyToOne
//	@JoinColumn(referencedColumnName = "idProduto", name = "idProduto")
//	private ProdutoModel produto;

	public ImagemModel(String caminho, Integer produto) {
		super();
		this.caminho = caminho.replace("\\", "/");
		this.idProduto = idProduto;
	}

	public ImagemModel(Integer id, String caminho, Integer idProduto) {
		super();
		this.id = id;
		this.caminho = caminho.replace("\\", "/");
		this.idProduto = idProduto;
	}

	public ImagemModel() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCaminho() {
		return caminho;
	}

	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}

//	public String getLogo() {
//		return logo;
//	}
//
//	public void setLogo(String logo) {
//		this.logo = logo;
//	}

	public Integer getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(Integer idProduto) {
		this.idProduto = idProduto;
	}
	
//	@Transient
//	public String getImagemPath() {
//		if(logo == null || id == null) return null;
//		
//		return "/imagem-salvas/" + id + "/" + logo;
//	}
	
	

}
