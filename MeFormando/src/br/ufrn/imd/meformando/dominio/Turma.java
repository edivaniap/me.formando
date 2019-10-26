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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(String instituicao) {
		this.instituicao = instituicao;
	}

	public int getAnoFormacao() {
		return anoFormacao;
	}

	public void setAnoFormacao(int anoFormacao) {
		this.anoFormacao = anoFormacao;
	}

	public int getSemestreFormacao() {
		return semestreFormacao;
	}

	public void setSemestreFormacao(int semestreFormacao) {
		this.semestreFormacao = semestreFormacao;
	}

	public int getCurso() {
		return curso;
	}

	public void setCurso(int curso) {
		this.curso = curso;
	}

	public int getQtdFormandos() {
		return qtdFormandos;
	}

	public void setQtdFormandos(int qtdFormandos) {
		this.qtdFormandos = qtdFormandos;
	}

	public List<Formando> getFormandos() {
		return formandos;
	}

	public void setFormandos(List<Formando> formandos) {
		this.formandos = formandos;
	}

	public Cerimonial getCerimonial() {
		return cerimonial;
	}

	public void setCerimonial(Cerimonial cerimonial) {
		this.cerimonial = cerimonial;
	}

	public List<ProjetoArrecadacao> getProjetosArrecadacoes() {
		return projetosArrecadacoes;
	}

	public void setProjetosArrecadacoes(List<ProjetoArrecadacao> projetosArrecadacoes) {
		this.projetosArrecadacoes = projetosArrecadacoes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Turma other = (Turma) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
