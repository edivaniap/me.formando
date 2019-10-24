package br.ufrn.imd.meformando.dominio;

import java.io.File;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Pagamento {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_PAGAMENTO")
	@SequenceGenerator(name="SEQ_PAGAMENTO", sequenceName="id_seq_pagamento", allocationSize=1)
	private int id;
	
	@OneToOne
	@JoinColumn(name="id_mensalidade")
	private Mensalidade mensalidade;
	
	@Temporal(TemporalType.DATE)
	private Date data;
	
	private boolean isVerificado;
	
	private File comprovante;

	public Pagamento() {
		// TODO Auto-generated constructor stub
	}

}
