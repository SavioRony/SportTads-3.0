package br.com.sporttads.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import br.com.sporttads.dto.ImagemDTO;
import javassist.bytecode.ByteArray;

@Entity
@Table(name = "tb_imagem")
public class ImagemModel {

	@Id
	@Column(name = "idImagem")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;

	private String nome;

	@Lob
	private ByteArray imagem;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public ByteArray getImagem() {
		return imagem;
	}

	public void setImagem(ByteArray imagem) {
		this.imagem = imagem;
	}

	public String getId() {
		return id;
	}

	public ImagemModel(String id, String nome, ByteArray imagem) {
		super();
		this.id = id;
		this.nome = nome;
		this.imagem = imagem;
	}

	public ImagemModel(ImagemDTO dto) {
		super();
		this.nome = dto.getNome();
		this.imagem = dto.getImagem();
	}

	public ImagemModel() {
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((imagem == null) ? 0 : imagem.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ImagemModel other = (ImagemModel) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (imagem == null) {
			if (other.imagem != null)
				return false;
		} else if (!imagem.equals(other.imagem))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ImagemModel [id=" + id + ", nome=" + nome + ", imagem=" + imagem + "]";
	}

}
