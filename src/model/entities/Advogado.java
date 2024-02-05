package model.entities;

import java.io.Serializable;
import java.util.Objects;

public class Advogado implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String nome;
	private String numero_Da_Ordem;

	public Advogado() {
	}

	public Advogado(Integer id, String nome, String numero_Da_Ordem) {
		super();
		this.id = id;
		this.nome = nome;
		this.numero_Da_Ordem = numero_Da_Ordem;
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

	public String getNumero_Da_Ordem() {
		return numero_Da_Ordem;
	}

	public void setNumero_Da_Ordem(String numero_Da_Ordem) {
		this.numero_Da_Ordem = numero_Da_Ordem;
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
		Advogado other = (Advogado) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Advogado [id=" + id + ", nome=" + nome + ", numero_Da_Ordem=" + numero_Da_Ordem + "]";
	}

}
