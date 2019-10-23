package br.ufrn.imd.meformando.dominio;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Tarefa {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_TAREFA")
	@SequenceGenerator(name="SEQ_TAREFA", sequenceName="id_seq_tarefa", allocationSize=1)
	private int id;
	
	private String descricao;
	private String status;
	
	public Tarefa() {
		// TODO Auto-generated constructor stub
	}

}
