package model.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import model.util.Status;

public class Processo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String numero_Processo;
	private Date data_De_Abertura;
	private String tipo;
	private Status status_Processo;
	private String juiz;
	private String descricao;
	private Double honorarios;
	private Double custos;
	
	private Cliente cliente_Id;
	
	private Advogado advogado_Id;
	
	private Parte_Processo parte;
	
	private Tribunal tribunal;
	
	private Usuario usuario;
	
	public Processo() {
	}

	public Processo(Integer id, String numero_Processo, Date data_De_Abertura, String tipo, Status status_Processo, String juiz,
			String descricao, Double honorarios, Double custos, Cliente cliente_Id, Advogado advogado_Id,
			Parte_Processo parte, Tribunal tribunal, Usuario usuario) {
		super();
		this.id = id;
		this.numero_Processo = numero_Processo;
		this.data_De_Abertura = data_De_Abertura;
		this.tipo = tipo;
		this.status_Processo = status_Processo;
		this.juiz = juiz;
		this.descricao = descricao;
		this.honorarios = honorarios;
		this.custos = custos;
		this.cliente_Id = cliente_Id;
		this.advogado_Id = advogado_Id;
		this.parte = parte;
		this.tribunal = tribunal;
		this.usuario = usuario;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNumero_Processo() {
		return numero_Processo;
	}

	public void setNumero_Processo(String numero_Processo) {
		this.numero_Processo = numero_Processo;
	}

	public Date getData_De_Abertura() {
		return data_De_Abertura;
	}

	public void setData_De_Abertura(Date data_De_Abertura) {
		this.data_De_Abertura = data_De_Abertura;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Status getStatus() {
		return status_Processo;
	}

	public void setStatus(Status status_Processo) {
		this.status_Processo = status_Processo;
	}

	public String getJuiz() {
		return juiz;
	}

	public void setJuiz(String juiz) {
		this.juiz = juiz;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getHonorarios() {
		return honorarios;
	}

	public void setHonorarios(Double honorarios) {
		this.honorarios = honorarios;
	}

	public Double getCustos() {
		return custos;
	}

	public void setCustos(Double custos) {
		this.custos = custos;
	}

	public Cliente getCliente_Id() {
		return cliente_Id;
	}

	public void setCliente_Id(Cliente cliente_Id) {
		this.cliente_Id = cliente_Id;
	}

	public Advogado getAdvogado_Id() {
		return advogado_Id;
	}

	public void setAdvogado_Id(Advogado advogado_Id) {
		this.advogado_Id = advogado_Id;
	}

	public Parte_Processo getPartes() {
		return parte;
	}

	public void setPartes(Parte_Processo parte) {
		this.parte = parte;
	}

	public Tribunal getTribunal() {
		return tribunal;
	}

	public void setTribunal(Tribunal tribunal) {
		this.tribunal = tribunal;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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
		Processo other = (Processo) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Processo [id=" + id + ", numero_Processo=" + numero_Processo + ", data_De_Abertura=" + data_De_Abertura
				+ ", tipo=" + tipo + ", status=" + status_Processo + ", juiz=" + juiz + ", descricao=" + descricao
				+ ", honorarios=" + honorarios + ", custos=" + custos + ", cliente_Id=" + cliente_Id + ", advogado_Id="
				+ advogado_Id + ", partes=" + parte + ", tribunal=" + tribunal + ", usuario=" + usuario + "]";
	}
	
	
}
