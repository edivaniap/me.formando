package br.ufrn.imd.meformando.dominio;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "tarefa")
public class Tarefa {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_TAREFA")
	@SequenceGenerator(name="SEQ_TAREFA", sequenceName="id_seq_tarefa", allocationSize=1)
	private int id;
	
	private String descricao;
	private String status;
		
	@ManyToOne
	@JoinColumn(name="id_projetoarrecadacao")
	private ProjetoArrecadacao projetoArrecadacao;
	
	@ManyToMany(mappedBy="tarefas")
    private List<Formando> equipe;
	
	public Tarefa() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ProjetoArrecadacao getProjetoArrecadacao() {
		return projetoArrecadacao;
	}

	public void setProjetoArrecadacao(ProjetoArrecadacao projetoArrecadacao) {
		this.projetoArrecadacao = projetoArrecadacao;
	}
	

	public List<Formando> getEquipe() {
		return equipe;
	}

	public void setEquipe(List<Formando> equipe) {
		this.equipe = equipe;
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
		Tarefa other = (Tarefa) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
