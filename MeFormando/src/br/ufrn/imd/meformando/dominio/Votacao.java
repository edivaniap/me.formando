package br.ufrn.imd.meformando.dominio;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity
public class Votacao {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_VOTACAO")
	@SequenceGenerator(name="SEQ_VOTACAO", sequenceName="id_seq_votacao", allocationSize=1)
	private int id;
	
	private String titulo;
	
	private boolean isFinalizada;
	
	private boolean isArquivada;
	
	@OneToMany(mappedBy = "votacao", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Opcao> opcoes;
	
	public Votacao() {
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

	public boolean isFinalizada() {
		return isFinalizada;
	}

	public void setFinalizada(boolean isFinalizada) {
		this.isFinalizada = isFinalizada;
	}

	public boolean isArquivada() {
		return isArquivada;
	}

	public void setArquivada(boolean isArquivada) {
		this.isArquivada = isArquivada;
	}

	public List<Opcao> getOpcoes() {
		return opcoes;
	}

	public void setOpcoes(List<Opcao> opcoes) {
		this.opcoes = opcoes;
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
		Votacao other = (Votacao) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
