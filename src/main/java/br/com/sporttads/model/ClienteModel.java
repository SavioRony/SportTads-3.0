package br.com.sporttads.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.*;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Data;

@Entity
@Table(name = "tb_cliente")
@Data
public class ClienteModel implements Serializable {
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

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario")
    private UsuarioModel usuario;

    @OneToMany(mappedBy = "id")
    private List<EnderecoModel> enderecos;

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

}
