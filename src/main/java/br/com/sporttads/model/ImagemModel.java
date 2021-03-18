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
	
	@Column(length = 45, nullable = true)
	private String img01;

	@Column(length = 45, nullable = true)
	private String img02;
	
	@Column(length = 45, nullable = true)
	private String img03;
	
	@Column(length = 45, nullable = true)
	private String img04;
	
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

	public String getImg01() {
		return img01;
	}

	public void setImg01(String img01) {
		this.img01 = img01;
	}

	public String getImg02() {
		return img02;
	}

	public void setImg02(String img02) {
		this.img02 = img02;
	}

	public String getImg03() {
		return img03;
	}

	public void setImg03(String img03) {
		this.img03 = img03;
	}

	public String getImg04() {
		return img04;
	}

	public void setImg04(String img04) {
		this.img04 = img04;
	}
	
	
	@Transient
	public String getImagemPath01() {
		if(img01 == null || idProduto == null) return null;
		System.out.println("/imagens-produtos/" + idProduto + "/" + img01);
		return "/imagens-produtos/" + idProduto + "/" + img01;
	}
	
	@Transient
	public String getImagemPath02() {
		if(img02 == null || idProduto == null) return null;
		
		return "/imagens-produtos/" + idProduto + "/" + img02;
	}
	
	@Transient
	public String getImagemPath03() {
		if(img03 == null || idProduto == null) return null;
		
		return "/imagens-produtos/" + idProduto + "/" + img03;
	}
	
	@Transient
	public String getImagemPath04() {
		if(img04 == null || idProduto == null) return null;
		
		return "/imagens-produtos/" + idProduto + "/" + img04;
	}
	

}
