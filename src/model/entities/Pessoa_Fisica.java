package model.entities;

import java.util.Date;
import java.util.Objects;

public class Pessoa_Fisica extends Cliente {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String cpf;
	private String rg;
	private String certidao_Casamento;
	private String ctps;
	private String cnh;
	private Date data_Nascimento;
	
	private Cliente cliente_Id;
	
	public Pessoa_Fisica() {
	}

	public Pessoa_Fisica(Integer id, String nome, String email, String telefone, String endereco, String cpf, String rg, String certidao_Casamento, String ctps, String cnh, Date data_Nascimento, Cliente cliente_Id) {
		super(id, nome, email, telefone, endereco);
		this.id = id;
		this.cpf = cpf;
		this.rg = rg;
		this.certidao_Casamento = certidao_Casamento;
		this.ctps = ctps;
		this.cnh = cnh;
		this.data_Nascimento = data_Nascimento;
		this.cliente_Id = cliente_Id;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getNome() {
		return super.nome;
	}
	
	public void setNome(String nome) {
		super.nome = nome;
	}
	
	public String getEmail() {
		return super.email;
	}
	
	public void setEmail(String email) {
		super.email = email;
	}
	
	public String getTeledone() {
		return super.telefone;
	}
	
	public void setTelefone(String telefone) {
		super.telefone = telefone;
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

	public String getCertidao_Casamento() {
		return certidao_Casamento;
	}

	public void setCertidao_Casamento(String certidao_Casamento) {
		this.certidao_Casamento = certidao_Casamento;
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
	
	public Date getData_nascimento() {
		return data_Nascimento;
	}
	
	public void setData_Nascimento(Date data_Nascimento) {
		this.data_Nascimento = data_Nascimento;
	}
	
	public Cliente getCliente_Id() {
		return cliente_Id;
	}

	public void setCliente_Id(Cliente cliente_Id) {
		this.cliente_Id = cliente_Id;
	}

	public Integer getId_Cliente(Cliente cliente) {
		return cliente.getId();
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
		return "Pessoa_Fisica [id=" + id + ", cpf=" + cpf + ", rg=" + rg + ", certidao_Casamento="
				+ certidao_Casamento + ", ctps=" + ctps + ", cnh=" + cnh + ", data_nascimento="+ data_Nascimento +", cliente="+ cliente_Id +"]";
	}

	
}
