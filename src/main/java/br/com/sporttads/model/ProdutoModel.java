package br.com.sporttads.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.sporttads.enumeration.StatusEnumeration;
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

	private Double preco;

	private Integer quantidade;

	private String categoria;

	private StatusEnumeration status;

	@OneToMany(targetEntity = ImagemModel.class)
	private List<ImagemModel> imagens;

	public ProdutoModel(Integer id, String nome, Double preco, Integer quantidade, String categoria) {
		super();
		this.id = id;
		this.nome = nome;
		this.preco = preco;
		this.quantidade = quantidade;
		this.categoria = categoria;
		this.status = StatusEnumeration.ATIVO;
	}

	public ProdutoModel() {
		this.status = StatusEnumeration.ATIVO;
	}

}
