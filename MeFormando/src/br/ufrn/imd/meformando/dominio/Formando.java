package br.ufrn.imd.meformando.dominio;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "formando")
public class Formando {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_FORMANDO")
	@SequenceGenerator(name="SEQ_FORMANDO", sequenceName="id_seq_formando", allocationSize=1)
	private int id;
	
	private String nome;
	
	@Column(unique = true)
	private String cpf;
	
	@Column(unique = true)
	private String email;
	
	private String senha;
	
	private boolean confirmadoTurma;
	
	private boolean isComissao;
	
	@ManyToOne
	@JoinColumn(name = "id_turma")
	@JsonIgnore
	private Turma turma;
	
	@OneToMany(mappedBy = "formando", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<Mensalidade> mensalidades;
	
	@OneToMany(mappedBy = "formando", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<Convite> convites;

	public List<Convite> getConvites() {
		return convites;
	}

	public void setConvites(List<Convite> convites) {
		this.convites = convites;
	}

	public Formando() {
		// TODO Auto-generated constructor stub
	}
	
	public Formando(String nome, String cpf, String email, String senha, boolean confirmadoTurma) {
		super();
		this.nome = nome;
		this.cpf = cpf;
		this.email = email;
		this.senha = senha;
		this.confirmadoTurma = confirmadoTurma;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public boolean isConfirmadoTurma() {
		return confirmadoTurma;
	}

	public void setConfirmadoTurma(boolean confirmadoTurma) {
		this.confirmadoTurma = confirmadoTurma;
	}

	public boolean isComissao() {
		return isComissao;
	}

	public void setComissao(boolean isComissao) {
		this.isComissao = isComissao;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	public List<Mensalidade> getMensalidades() {
		return mensalidades;
	}

	public void setMensalidades(List<Mensalidade> mensalidades) {
		this.mensalidades = mensalidades;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
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
		Formando other = (Formando) obj;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		if (id != other.id)
			return false;
		return true;
	}
	
}
