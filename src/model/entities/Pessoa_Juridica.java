package model.entities;

import java.util.Objects;

public class Pessoa_Juridica extends Cliente {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String nome_Fantasia;
	private String cnpj;
	
	private Cliente cliente_Id;
	
	public Pessoa_Juridica() {
	}

	public Pessoa_Juridica(Integer id, String nome, String email, String telefone, String endereco, String tipo, String nome_Fantasia, String cnpj, Cliente cliente_Id) {
		super(id, nome, email, telefone, endereco, tipo);
		this.nome_Fantasia = nome_Fantasia;
		this.cnpj = cnpj;
		this.cliente_Id = cliente_Id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome_Fantasia() {
		return nome_Fantasia;
	}

	public void setNome_Fantasia(String nome_Fantasia) {
		this.nome_Fantasia = nome_Fantasia;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	
	public Cliente getCliente_Id() {
		return cliente_Id;
	}
	
	public void setCliente_Id(Cliente cliente_Id) {
		this.cliente_Id = cliente_Id;
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
		return "Pessoa_Juridica [id=" + id + ", nome_Fantasia=" + nome_Fantasia + ", cnpj=" + cnpj + ", cliente="+ cliente_Id +"]";
	}
	
	
	
}
