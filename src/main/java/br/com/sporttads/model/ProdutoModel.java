package br.com.sporttads.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.sporttads.enumeration.StatusEnumeration;
import lombok.Data;

@Entity
@Table(name = "TB_PRODUTO")
@Data
public class ProdutoModel {

	@Id
	@Column(name = "ID_PRODUTO")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String nome;

	private String descricao;

	private Double preco;

	private Integer quantidade;

	private Integer estrelas;

	private String categoria;

	private StatusEnumeration status;

	@OneToMany
	@JoinColumn(name = "PRODUTO_ID_PRODUTO")
	private List<ImagemModel> imagens;

	public ProdutoModel(Integer id, String nome, String descricao, Double preco, Integer quantidade, Integer estrelas,
			String categoria) {
		super();
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.preco = preco;
		this.quantidade = quantidade;
		this.estrelas = estrelas;
		this.categoria = categoria;
		this.status = StatusEnumeration.ATIVO;
	}

	public ProdutoModel() {
		this.status = StatusEnumeration.ATIVO;
	}

}
