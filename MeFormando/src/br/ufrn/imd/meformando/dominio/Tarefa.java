package br.ufrn.imd.meformando.dominio;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity
public class Tarefa {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_TAREFA")
	@SequenceGenerator(name="SEQ_TAREFA", sequenceName="id_seq_tarefa", allocationSize=1)
	private int id;
	
	private String descricao;
	private String status;
	
	@ManyToOne
	@JoinColumn(name="id_equipe")
	private Equipe equipe;
	
	@ManyToOne
	@JoinColumn(name="id_projetoarrecadacao")
	private ProjetoArrecadacao projetoArrecadacao;
	
	public Tarefa() {
		// TODO Auto-generated constructor stub
	}

}
