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

}
