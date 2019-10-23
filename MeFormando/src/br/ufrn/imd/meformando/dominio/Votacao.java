package br.ufrn.imd.meformando.dominio;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Votacao {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_VOTACAO")
	@SequenceGenerator(name="SEQ_VOTACAO", sequenceName="id_seq_votacao", allocationSize=1)
	private int id;
	
	private String titulo;
	private String status;
	
	public Votacao() {
		// TODO Auto-generated constructor stub
	}

}
