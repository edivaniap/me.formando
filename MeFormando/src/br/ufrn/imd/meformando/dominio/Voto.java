package br.ufrn.imd.meformando.dominio;

import java.util.Date;

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
@Table(name = "voto")
public class Voto {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_VOTO")
	@SequenceGenerator(name="SEQ_VOTO", sequenceName="id_seq_voto", allocationSize=1)
	private int id;
	
	@Temporal(TemporalType.DATE)
	private Date data;
	
	@ManyToOne
	@JoinColumn(name = "id_formando")
	private Formando formando;
	
	@ManyToOne
	@JoinColumn(name = "id_opcao")
	private Opcao opcao;

	public Voto() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Formando getFormando() {
		return formando;
	}

	public void setFormando(Formando formando) {
		this.formando = formando;
	}

	public Opcao getOpcao() {
		return opcao;
	}

	public void setOpcao(Opcao opcao) {
		this.opcao = opcao;
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
		Voto other = (Voto) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
