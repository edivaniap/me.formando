package br.ufrn.imd.meformando.dominio;

import java.io.File;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "cerimonial")
public class Cerimonial {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_CERIMONIAL")
	@SequenceGenerator(name="SEQ_CERIMONIAL", sequenceName="id_seq_cerimonial", allocationSize=1)
	private int id;
	
	private String nome;
	
	private File contrato;
	
	private double custo;
	
	private String descricao;

	@OneToMany(mappedBy = "cerimonial", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<EventoComemoracao> eventosComemoracoes;
	
	public Cerimonial() {
		// TODO Auto-generated constructor stub
	}
	

	public Cerimonial(String nome, double custo, String descricao) {
		super();
		this.nome = nome;
		this.custo = custo;
		this.descricao = descricao;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public File getContrato() {
		return contrato;
	}

	public void setContrato(File contrato) {
		this.contrato = contrato;
	}

	public double getCusto() {
		return custo;
	}

	public void setCusto(double custo) {
		this.custo = custo;
	}

	public List<EventoComemoracao> getEventosComemoracoes() {
		return eventosComemoracoes;
	}

	public void setEventosComemoracoes(List<EventoComemoracao> eventosComemoracoes) {
		this.eventosComemoracoes = eventosComemoracoes;
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
		Cerimonial other = (Cerimonial) obj;
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
