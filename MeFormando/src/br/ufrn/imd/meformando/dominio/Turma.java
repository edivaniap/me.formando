package br.ufrn.imd.meformando.dominio;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
	
	@OneToMany(mappedBy = "turma", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Formando> formandos;
	
	@ManyToOne
	@JoinColumn(name="id_cerimonial")
	private Cerimonial cerimonial;
	
	@OneToMany(mappedBy = "turma", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ProjetoArrecadacao> projetosArrecadacoes;

	public Turma() {
		// TODO Auto-generated constructor stub
	}

}
