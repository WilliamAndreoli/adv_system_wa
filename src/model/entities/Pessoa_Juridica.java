package model.entities;

import java.util.Objects;

public class Pessoa_Juridica extends Cliente{
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String nome_fantasia;
	private String cnpj;
	
	public Pessoa_Juridica() {
	}

	public Pessoa_Juridica(Integer id, String nome, String email, String telefone, String endereco, String nome_fantasia, String cnpj) {
		super(id, nome, email, telefone, endereco);
		this.nome_fantasia = nome_fantasia;
		this.cnpj = cnpj;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome_fantasia() {
		return nome_fantasia;
	}

	public void setNome_fantasia(String nome_fantasia) {
		this.nome_fantasia = nome_fantasia;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(id);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pessoa_Juridica other = (Pessoa_Juridica) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Pessoa_Juridica [id=" + id + ", nome_fantasia=" + nome_fantasia + ", cnpj=" + cnpj + "]";
	}
	
	
	
}
