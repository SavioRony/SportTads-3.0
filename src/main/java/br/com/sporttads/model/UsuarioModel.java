package br.com.sporttads.model;

import javax.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name = "tb_usuario")
public class UsuarioModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nome;

	@Column(nullable = false, unique = true)
	private String email;

	private String senha;

	private String tipo;

	private String status;

	@Column(name = "codigo_verificador", length = 6)
	private String codigoVerificador;

	public UsuarioModel() {
		this.status = "Ativo";
	}

	public UsuarioModel(String email) {
		this.email = email;
	}

	public UsuarioModel(String nome, String email, String senha, String tipo, String status) {
		super();
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.tipo = tipo;
		this.status = status;
	}

}
