package br.ufrn.imd.meformando.dominio;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "convite")
public class Convite {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_CONVITE")
	@SequenceGenerator(name="SEQ_CONVITE", sequenceName="id_seq_convite", allocationSize=1)
	private int id;
	
	private int  idDaTurma;

	private String formandoQueConvidou;
	
	@ManyToOne
	@JoinColumn(name = "id_formando")
	private Formando formando;
	

	public Convite() {
		
	}

	public Convite(int idDaTurma, String formandoQueConvidou,Formando formando) {
		super();
		this.idDaTurma = idDaTurma;
		this.formandoQueConvidou = formandoQueConvidou;
		this.formando = formando;
	}


	public int getIdDaTurma() {
		return idDaTurma;
	}


	public void setIdDaTurma(int idDaTurma) {
		this.idDaTurma = idDaTurma;
	}


	public String getFormandoQueConvidou() {
		return formandoQueConvidou;
	}


	public void setFormandoQueConvidou(String formandoQueConvidou) {
		this.formandoQueConvidou = formandoQueConvidou;
	}


	public int getId() {
		return id;
	}


	
	
	
	

}
