package br.com.sporttads.model;

import javax.persistence.*;

@Entity
public class UsuarioModel extends EntidadeAbstrata<Long> {

	@Column(nullable = false, unique = true)
	private String nome;

	@Column(nullable = false, unique = true)
	private String email;

	private String senha;

	private String tipoDeFuncionario;

	private String status;

	public UsuarioModel() {
		this.status = "Ativo";
	}

	public UsuarioModel(String nome, String email, String senha, String tipoDeFuncionario, String status) {
		super();
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.tipoDeFuncionario = tipoDeFuncionario;
		this.status = status;
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

	public String getTipoDeFuncionario() {
		return tipoDeFuncionario;
	}

	public void setTipoDeFuncionario(String tipoDeFuncionario) {
		this.tipoDeFuncionario = tipoDeFuncionario;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
