package br.ufrn.imd.meformando.dominio;

import java.io.File;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Cerimonial {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_CERIMONIAL")
	@SequenceGenerator(name="SEQ_CERIMONIAL", sequenceName="id_seq_cerimonial", allocationSize=1)
	private int id;
	
	private String nome;
	
	//rever contrato, pois existe contrato com a turma e com cada formando
	private File contrato;
	
	private double custo;
	
	public Cerimonial() {
		// TODO Auto-generated constructor stub
	}

}
