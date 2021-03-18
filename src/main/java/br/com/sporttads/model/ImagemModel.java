package br.com.sporttads.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "TB_IMAGEM")
@Data
public class ImagemModel {

	@Id
	@Column(name = "ID_IMAGEM")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String caminho;

	
	@ManyToOne
	@JoinColumn(name = "ID_PRODUTO")
	private ProdutoModel produto;

	public ImagemModel(String caminho, ProdutoModel produto) {
		super();
		this.caminho = caminho.replace("\\", "/");
		this.produto = produto;
	}

	public ImagemModel(Integer id, String caminho, ProdutoModel produto) {
		super();
		this.id = id;
		this.caminho = caminho.replace("\\", "/");
		this.produto = produto;
	}

	public ImagemModel() {
	}

}
