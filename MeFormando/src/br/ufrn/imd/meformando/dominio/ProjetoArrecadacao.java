package br.ufrn.imd.meformando.dominio;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "projetoArrecadacao")
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
	
	@OneToMany(mappedBy = "projetoArrecadacao", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Tarefa> tarefas;
	
	@ManyToOne
	private Turma turma;
	
	public ProjetoArrecadacao() {
		// TODO Auto-generated constructor stub
	}
	
	public ProjetoArrecadacao(String titulo, double custo, double ganho, Date dataInicial, Date dataFinal) {
		super();
		this.titulo = titulo;
		this.custo = custo;
		this.ganho = ganho;
		this.dataInicial = dataInicial;
		this.dataFinal = dataFinal;
		
	}
	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
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

	public double getCusto() {
		return custo;
	}

	public void setCusto(double custo) {
		this.custo = custo;
	}

	public double getGanho() {
		return ganho;
	}

	public void setGanho(double ganho) {
		this.ganho = ganho;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public List<Tarefa> getTarefas() {
		return tarefas;
	}

	public void setTarefas(List<Tarefa> tarefas) {
		this.tarefas = tarefas;
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
		ProjetoArrecadacao other = (ProjetoArrecadacao) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
