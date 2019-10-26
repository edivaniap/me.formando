package br.ufrn.imd.meformando.dominio;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class Opcao {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_OPCAO")
	@SequenceGenerator(name="SEQ_OPCAO", sequenceName="id_seq_opcao", allocationSize=1)
	private int id;
	
	private String titulo;
	
	private String qtdVotos;
	
	@ManyToOne
	@JoinColumn(name = "id_votacao")
	private Votacao votacao;


	public Opcao() {
		// TODO Auto-generated constructor stub
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


	public String getQtdVotos() {
		return qtdVotos;
	}


	public void setQtdVotos(String qtdVotos) {
		this.qtdVotos = qtdVotos;
	}


	public Votacao getVotacao() {
		return votacao;
	}


	public void setVotacao(Votacao votacao) {
		this.votacao = votacao;
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
		Opcao other = (Opcao) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
