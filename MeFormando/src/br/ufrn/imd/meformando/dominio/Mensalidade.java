package br.ufrn.imd.meformando.dominio;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Mensalidade {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_MENSALIDADE")
	@SequenceGenerator(name="SEQ_MENSALIDADE", sequenceName="id_seq_mensalidade", allocationSize=1)
	private int id;
	
	private double valor;
	
	@Temporal(TemporalType.DATE)
	private Date mes;
	
	private boolean isPago;
	
	@OneToOne(mappedBy = "mensalidade", cascade = CascadeType.ALL, optional = false)
	private Pagamento pagamento;
	
	@ManyToOne
	@JoinColumn(name = "id_formando")
	private Formando formando;
	
	public Mensalidade() {
		// TODO Auto-generated constructor stub
	}

}
