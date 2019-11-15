package br.ufrn.imd.meformando.repositories;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import br.ufrn.imd.meformando.dominio.Cerimonial;


@Stateless
public class CerimonialRepository {

	@PersistenceContext
	private EntityManager em;
	
	public Cerimonial adicionar(Cerimonial cerimonial) {
		if(cerimonial.getId() == 0)
			em.persist(cerimonial);
		else
			em.merge(cerimonial);
		return cerimonial;
	}
	
	public Cerimonial alterar(Cerimonial cerimonial) {
		
		return cerimonial;
	}
	
	public void remover(Cerimonial cerimonial) {
		cerimonial = em.find(Cerimonial.class, cerimonial.getId());
		em.remove(cerimonial);
	}
	
	@SuppressWarnings("unchecked")
	public List<Cerimonial> listar() {
		return (List<Cerimonial>) em.createQuery("select c from Cerimonial c").getResultList();
	}

	


}