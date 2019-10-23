package br.ufrn.imd.meformando.dominio;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class Formando {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_FORMANDO")
	@SequenceGenerator(name="SEQ_FORMANDO", sequenceName="id_seq_formando", allocationSize=1)
	private int id;
	
	private String nome;
	private String cpf;
	private String email;
	private String senha;
	private boolean confirmadoTurma;
	private boolean isComissao;
	
	@ManyToOne
	@JoinColumn(name = "id_turma")
	private Turma turma;

	public Formando() {
		// TODO Auto-generated constructor stub
	}

}
