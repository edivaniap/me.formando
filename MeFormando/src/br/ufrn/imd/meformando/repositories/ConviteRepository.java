package br.ufrn.imd.meformando.repositories;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.ufrn.imd.meformando.dominio.Convite;


@Stateless
public class ConviteRepository {
	
	@PersistenceContext
	private EntityManager em;
	
	public Convite adicionar(Convite convite) {
		if(convite.getId() == 0)
			em.persist(convite);
		else
			em.merge(convite);
		return convite;
	}
	
	public Convite alterar(Convite convite) {
		em.persist(convite);
		return convite;
	}
	
	public void remover(Convite convite) {
		convite = em.find(Convite.class, convite.getId());
		em.remove(convite);
	}
	
	public List<Convite> findConviteByFormando(int id_formando){
		try {
			String jpaql ="select f from Convite f where f.formando.id = :id_formando";
			Query q = em.createQuery(jpaql);
			q.setParameter("id_formando", id_formando);
			@SuppressWarnings("unchecked")
			List<Convite> resultList = (List<Convite>) q.getResultList();
			return resultList;
		} catch (NoResultException e) {
			return null;
		}	
	}

	public Convite findConviteById(int id) {
		try {
			String jpaql ="select f from Convite f where f.id = :id";
			Query q = em.createQuery(jpaql);
			q.setParameter("id", id);
			return (Convite) q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
