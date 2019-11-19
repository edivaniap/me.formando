package br.ufrn.imd.meformando.dominio;

import java.io.File;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "pagamento")
public class Pagamento {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_PAGAMENTO")
	@SequenceGenerator(name="SEQ_PAGAMENTO", sequenceName="id_seq_pagamento", allocationSize=1)
	private int id;
	
	@OneToOne
	@JoinColumn(name="id_mensalidade")
	private Mensalidade mensalidade;
	
	@Temporal(TemporalType.DATE)
	private Date data;
	
	private boolean isVerificado;
	
	private File comprovante;

	public Pagamento() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Mensalidade getMensalidade() {
		return mensalidade;
	}

	public void setMensalidade(Mensalidade mensalidade) {
		this.mensalidade = mensalidade;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public boolean isVerificado() {
		return isVerificado;
	}

	public void setVerificado(boolean isVerificado) {
		this.isVerificado = isVerificado;
	}

	public File getComprovante() {
		return comprovante;
	}

	public void setComprovante(File comprovante) {
		this.comprovante = comprovante;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pagamento other = (Pagamento) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
}
