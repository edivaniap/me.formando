package br.ufrn.imd.meformando.dominio;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "mensalidade")
public class Mensalidade {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_MENSALIDADE")
	@SequenceGenerator(name="SEQ_MENSALIDADE", sequenceName="id_seq_mensalidade", allocationSize=1)
	private int id;
	
	private double valor;
	
	@Temporal(TemporalType.DATE)
	private Date mes;
	
	private boolean isPago;
	
	@OneToOne(mappedBy = "mensalidade", cascade = CascadeType.ALL, optional = false)
	private Pagamento pagamento;
	
	@ManyToOne
	@JoinColumn(name = "id_formando")
	private Formando formando;
	
	public Mensalidade() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public Date getMes() {
		return mes;
	}

	public void setMes(Date mes) {
		this.mes = mes;
	}

	public boolean isPago() {
		return isPago;
	}

	public void setPago(boolean isPago) {
		this.isPago = isPago;
	}

	public Pagamento getPagamento() {
		return pagamento;
	}

	public void setPagamento(Pagamento pagamento) {
		this.pagamento = pagamento;
	}

	public Formando getFormando() {
		return formando;
	}

	public void setFormando(Formando formando) {
		this.formando = formando;
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
		Mensalidade other = (Mensalidade) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
}
