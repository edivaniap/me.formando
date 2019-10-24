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
public class Equipe {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_EQUIPE")
	@SequenceGenerator(name="SEQ_EQUIPE", sequenceName="id_seq_equipe", allocationSize=1)
	private int id;
	
	private String nome;
	
	@OneToMany(mappedBy = "equipe", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Tarefa> tarefas;
	
	public Equipe() {
		// TODO Auto-generated constructor stub
	}

}
