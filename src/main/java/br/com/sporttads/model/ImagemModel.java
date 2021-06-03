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
	private String nomeImagem;
	private Integer idProduto;


	public ImagemModel() {
	}

	public ImagemModel(String nomeImagem, Integer idProduto) {
		this.nomeImagem = nomeImagem;
		this.idProduto = idProduto;
		setCaminho();
	}

	public String getCaminho() {
		return caminho;
	}

	public void setCaminho() {
		this.caminho = "/imagens-produto/" + getIdProduto() + "/" + getNomeImagem();
	}

	public String getNomeImagem() {
		return nomeImagem;
	}

	public void setNomeImagem(String nomeImagem) {
		this.nomeImagem = nomeImagem;
	}
}
