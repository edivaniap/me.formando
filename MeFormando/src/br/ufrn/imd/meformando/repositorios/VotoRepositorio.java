package br.ufrn.imd.meformando.repositorios;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.ufrn.imd.meformando.dominio.Voto;
import br.ufrn.imd.meformando.util.CryptService;

@Stateless
public class VotoRepositorio {

	@PersistenceContext
	private EntityManager em;
	
	public Voto adicionar(Voto voto) {
		if(voto.getId() == 0)
			em.persist(voto);
		else
			em.merge(Voto);
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