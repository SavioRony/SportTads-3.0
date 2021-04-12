package br.com.sporttads.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.hibernate.validator.constraints.br.CPF;

@Entity
@Table(name="tb_cliente")
public class ClienteModel implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nomeCompleto;
	
	@CPF(message = "CPF INVÁLIDO")
	@Column(unique = true)
	private String cpf;
	
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate dataNascimento;
	
	private String telPrincipal;

	@OneToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "id_usuario")
	private UsuarioModel usuario;

	//private List<enderecos> enderecos;
	
	public ClienteModel() {
		super();
	}

	public ClienteModel(Long id, String nomeCompleto, String cpf, LocalDate dataNascimento, String telPrincipal) {
		super();
		this.id = id;
		this.nomeCompleto = nomeCompleto;
		this.cpf = cpf;
		this.dataNascimento = dataNascimento;
		this.telPrincipal = telPrincipal;
	}

	public boolean hasNotId() {
		return id == null;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id= id;
	}

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getTelPrincipal() {
		return telPrincipal;
	}

	public void setTelPrincipal(String telPrincipal) {
		this.telPrincipal = telPrincipal;
	}

	public UsuarioModel getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioModel usuario) {
		this.usuario = usuario;
	}
	
	public static void teste() {
	System.out.println("testando");
	}

	

	
	

}
