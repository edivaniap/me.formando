package br.ufrn.imd.meformando.dominio;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Voto {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_VOTO")
	@SequenceGenerator(name="SEQ_VOTO", sequenceName="id_seq_voto", allocationSize=1)
	private int id;
	
	@Temporal(TemporalType.DATE)
	private Date data;
	
	@ManyToOne
	@JoinColumn(name = "id_formando")
	private Formando formando;
	
	@ManyToOne
	@JoinColumn(name = "id_opcao")
	private Opcao opcao;

	public Voto() {
		// TODO Auto-generated constructor stub
	}

}
