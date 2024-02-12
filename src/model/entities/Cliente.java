package model.entities;

import java.io.Serializable;
import java.util.Objects;

public class Cliente implements Serializable {
	private static final long serialVersionUID = 1L;
	
	protected Integer id;
	protected String nome;
	protected String email;
	protected String telefone;
	protected String endereco;
	protected String tipo;
	
	public Cliente() {
	}

	public Cliente(Integer id, String nome, String email, String telefone, String endereco, String tipo) {
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
		this.endereco = endereco;
		this.tipo = tipo;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
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
		Cliente other = (Cliente) obj;
		return Objects.equals(id, other.id);
	}

	//Alterado para listar em Processos
	@Override
	public String toString() {
		return nome;
	}

	
	
}
