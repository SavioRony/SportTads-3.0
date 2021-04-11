package br.com.sporttads.model;

import javax.persistence.*;

@Entity
@Table(name = "tb_usuario", indexes = { @Index(name = "idx_usuario_email", columnList = "email") })
public class UsuarioModel extends EntidadeAbstrata<Long> {

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

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getTipo() {
		return tipo;
	}

	public String getCodigoVerificador() {
		return codigoVerificador;
	}

	public void setCodigoVerificador(String codigoVerificador) {
		this.codigoVerificador = codigoVerificador;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
