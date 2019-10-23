package br.ufrn.imd.meformando.dominio;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

public class Turma {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_TURMA")
	@SequenceGenerator(name="SEQ_TURMA", sequenceName="id_seq_turma", allocationSize=1)
	private int id;
	
	private String titulo;
	private String instituicao;
	private int anoFormacao;
	private	int semestreFormacao;
	private int curso;
	private int qtdFormandos;

	public Turma() {
		// TODO Auto-generated constructor stub
	}

}
