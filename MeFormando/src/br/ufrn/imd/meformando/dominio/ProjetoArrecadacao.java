package br.ufrn.imd.meformando.dominio;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class ProjetoArrecadacao {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_PROJETOARRECADACAO")
	@SequenceGenerator(name="SEQ_PROJETOARRECADACAO", sequenceName="id_seq_projetoarrecadacao", allocationSize=1)
	private int id;
	
	private String titulo;
	private double custo;
	private double ganho;

	@Temporal(TemporalType.DATE)
	private Date dataInicial;

	@Temporal(TemporalType.DATE)
	private Date dataFinal;
	
	public ProjetoArrecadacao() {
		// TODO Auto-generated constructor stub
	}

}
