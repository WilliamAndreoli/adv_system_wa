package model.entities;

import java.util.Objects;

public class Pessoa_Fisica extends Cliente {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String cpf;
	private String rg;
	private String certidao_nascimento;
	private String ctps;
	private String cnh;
	
	public Pessoa_Fisica() {
	}

	public Pessoa_Fisica(Integer id, String cpf, String rg, String certidao_nascimento, String ctps, String cnh) {
		super();
		this.id = id;
		this.cpf = cpf;
		this.rg = rg;
		this.certidao_nascimento = certidao_nascimento;
		this.ctps = ctps;
		this.cnh = cnh;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getCertidao_nascimento() {
		return certidao_nascimento;
	}

	public void setCertidao_nascimento(String certidao_nascimento) {
		this.certidao_nascimento = certidao_nascimento;
	}

	public String getCtps() {
		return ctps;
	}

	public void setCtps(String ctps) {
		this.ctps = ctps;
	}

	public String getCnh() {
		return cnh;
	}

	public void setCnh(String cnh) {
		this.cnh = cnh;
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
		Pessoa_Fisica other = (Pessoa_Fisica) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Pessoa_Fisica [id=" + id + ", cpf=" + cpf + ", rg=" + rg + ", certidao_nascimento="
				+ certidao_nascimento + ", ctps=" + ctps + ", cnh=" + cnh + "]";
	}

	
}
