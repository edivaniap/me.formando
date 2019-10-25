package br.ufrn.imd.meformando.repositorios;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.ufrn.imd.meformando.dominio.Formando;

@Stateless
public class FormandoRepositorio {

	@PersistenceContext
	private EntityManager em;
	
	public Formando adicionar(Formando formando) {
		if(formando.getId() == 0)
			em.persist(formando);
		else
			em.merge(formando);
		return formando;
	}
	
	public void remover(Formando formando) {
		formando = em.find(Formando.class, formando.getId());
		em.remove(formando);
	}
	
	@SuppressWarnings("unchecked")
	public List<Formando> listar() {
		return (List<Formando>) em.createQuery("select f from Formando f").getResultList();
	}	
	
	public Formando buscarMaterial(String codigo) {
		String jpaql ="select f from Formando f"
				+ " where f.codigo = :codigo";
		
		Query q = em.createQuery(jpaql);
		q.setParameter("codigo", codigo);
		
		try {
			return (Formando) q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
