package br.com.sporttads.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@Entity
@Table(name = "tb_imagem")
@Data
public class ImagemModel {

	@Id
	@Column(name = "idImagem")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

	@Transient
	public String getImagemPath01() {
		if (img01 == null || idProduto == null)
			return null;
		return "/imagens-produto/" + idProduto + "/" + img01;
	}

	@Transient
	public String getImagemPath02() {
		if (img02 == null || idProduto == null)
			return null;

		return "/imagens-produto/" + idProduto + "/" + img02;
	}

	@Transient
	public String getImagemPath03() {
		if (img03 == null || idProduto == null)
			return null;

		return "/imagens-produto/" + idProduto + "/" + img03;
	}

	@Transient
	public String getImagemPath04() {
		if (img04 == null || idProduto == null)
			return null;

		return "/imagens-produto/" + idProduto + "/" + img04;
	}

}
