package br.com.sporttads.dto;

import javassist.bytecode.ByteArray;

public class ImagemDTO {

	private String nome;

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

	public ImagemDTO(String nome, ByteArray imagem) {
		super();
		this.nome = nome;
		this.imagem = imagem;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		ImagemDTO other = (ImagemDTO) obj;
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
		return "ImagemDTO [nome=" + nome + ", imagem=" + imagem + "]";
	}

}
