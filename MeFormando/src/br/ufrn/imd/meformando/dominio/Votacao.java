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

}
