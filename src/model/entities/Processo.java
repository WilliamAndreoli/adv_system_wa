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
	private Status status;
	private String juiz;
	private String descricao;
	private Double honorarios;
	private Double custos;
	
	private Cliente cliente_Id;
	
	private List<Advogado> advogados;
	
	private List<Parte_Processo> partes;
	
	private Tribunal tribunal;
	
	private Usuario usuario;
	
	public Processo() {
	}

	public Processo(Integer id, String numero_Processo, Date data_De_Abertura, String tipo, Status status, String juiz,
			String descricao, Double honorarios, Double custos, Cliente cliente_Id, List<Advogado> advogados,
			List<Parte_Processo> partes, Tribunal tribunal, Usuario usuario) {
		super();
		this.id = id;
		this.numero_Processo = numero_Processo;
		this.data_De_Abertura = data_De_Abertura;
		this.tipo = tipo;
		this.status = status;
		this.juiz = juiz;
		this.descricao = descricao;
		this.honorarios = honorarios;
		this.custos = custos;
		this.cliente_Id = cliente_Id;
		this.advogados = advogados;
		this.partes = partes;
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
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
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

	public List<Advogado> getAdvogados() {
		return advogados;
	}

	public void setAdvogados(List<Advogado> advogados) {
		this.advogados = advogados;
	}

	public List<Parte_Processo> getPartes() {
		return partes;
	}

	public void setPartes(List<Parte_Processo> partes) {
		this.partes = partes;
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
				+ ", tipo=" + tipo + ", status=" + status + ", juiz=" + juiz + ", descricao=" + descricao
				+ ", honorarios=" + honorarios + ", custos=" + custos + ", cliente_Id=" + cliente_Id + ", advogados="
				+ advogados + ", partes=" + partes + ", tribunal=" + tribunal + ", usuario=" + usuario + "]";
	}
	
	
}
