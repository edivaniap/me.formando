package br.ufrn.imd.meformando.dominio;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "evento")
public class Evento {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_EVENTOCOMEMORACAO")
	@SequenceGenerator(name="SEQ_EVENTOCOMEMORACAO", sequenceName="id_seq_eventocomemoracao", allocationSize=1)
	private int id;
	
	//@Column(unique = true)
	private String titulo;
	
	private double custo;
	
	//@Temporal(TemporalType.DATE)
	private Date data;

	private String descricao;
	
	
	@ManyToOne
	@JoinColumn(name = "id_cerimonial")
	private Cerimonial cerimonial;
	
	public Cerimonial getCerimonial() {
		return cerimonial;
	}



	public void setCerimonial(Cerimonial cerimonial) {
		this.cerimonial = cerimonial;
	}



	public Evento() {
		// TODO Auto-generated constructor stub
	}
	
	

	public Evento(String titulo, double custo, Date data, String descricao) {
		super();
		this.titulo = titulo;
		this.custo = custo;
		this.data = data;
		this.descricao = descricao;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public double getCusto() {
		return custo;
	}

	public void setCusto(double custo) {
		this.custo = custo;
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
		Evento other = (Evento) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}
