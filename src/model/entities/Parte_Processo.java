package model.entities;

import java.io.Serializable;
import java.util.Objects;

public class Parte_Processo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String nome;
	
	public Parte_Processo() {
	}

	public Parte_Processo(Integer id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
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
		Parte_Processo other = (Parte_Processo) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Parte_Processo [id=" + id + ", nome=" + nome + "]";
	}
	
	
}
