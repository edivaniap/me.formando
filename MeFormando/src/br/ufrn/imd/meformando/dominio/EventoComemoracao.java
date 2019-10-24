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
public class EventoComemoracao {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_EVENTOCOMEMORACAO")
	@SequenceGenerator(name="SEQ_EVENTOCOMEMORACAO", sequenceName="id_seq_eventocomemoracao", allocationSize=1)
	private int id;
	
	private String titulo;
	
	@Temporal(TemporalType.DATE)
	private Date data;
	
	private double custo;
	
	public EventoComemoracao() {
		// TODO Auto-generated constructor stub
	}

}
