package br.ufrn.imd.meformando.repositorios;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.ufrn.imd.meformando.dominio.Mensalidade;

@Stateless
public class MensalidadeRepositorio {
	
	@PersistenceContext
	private EntityManager em;
	
	public Mensalidade adicionar(Mensalidade mensalidade) {
		
		if(mensalidade.getId() == 0)
			em.persist(mensalidade);
		else
			em.merge(mensalidade);
		return mensalidade;
	}
	
	public void remover(Mensalidade mensalidade) {
		mensalidade = em.find(Mensalidade.class, mensalidade.getId());
		em.remove(mensalidade);
	}
	
	@SuppressWarnings("unchecked")
	public List<Mensalidade> listar() {
		return (List<Mensalidade>) em.createQuery("select m from Mensalidade m").getResultList();
	}	
	
	//Ver se vai precisa desse buscar
//	public Mensalidade findMensalidadeByStatus(boolean status) {
//		
//	}
}
