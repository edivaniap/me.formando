package br.ufrn.imd.meformando.dominio;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Equipe {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_EQUIPE")
	@SequenceGenerator(name="SEQ_EQUIPE", sequenceName="id_seq_equipe", allocationSize=1)
	private int id;
	
	private String nome;
	
	public Equipe() {
		// TODO Auto-generated constructor stub
	}

}
