package br.ufrn.imd.meformando.repositories;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.ufrn.imd.meformando.dominio.Voto;

@Stateless
public class VotoRepository {

	@PersistenceContext
	private EntityManager em;
	
	public Voto adicionar(Voto voto) {
		if(voto.getId() == 0)
			em.persist(voto);
		else
			em.merge(voto);
		return voto;
	}
	
	public void remover(Voto voto) {
		voto = em.find(Voto.class, voto.getId());
		em.remove(voto);
	}
	
	@SuppressWarnings("unchecked")
	public List<Voto> listar() {
		return (List<Voto>) em.createQuery("select v from Voto v").getResultList();
	}	

}