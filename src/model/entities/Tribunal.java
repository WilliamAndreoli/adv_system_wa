package model.entities;

import java.io.Serializable;
import java.util.Objects;

public class Tribunal implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String nome;
	private String endereco;
	private String jurisdicao;
	
	public Tribunal() {
	}

	public Tribunal(Integer id, String nome, String endereco, String jurisdicao) {
		super();
		this.id = id;
		this.nome = nome;
		this.endereco = endereco;
		this.jurisdicao = jurisdicao;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getJurisdicao() {
		return jurisdicao;
	}

	public void setJurisdicao(String jurisdicao) {
		this.jurisdicao = jurisdicao;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tribunal other = (Tribunal) obj;
		return Objects.equals(id, other.id);
	}

	//Alterado para listar em Processos
	@Override
	public String toString() {
		return nome;
	}
	
	
	
}
