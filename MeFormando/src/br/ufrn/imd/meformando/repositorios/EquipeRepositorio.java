package br.ufrn.imd.meformando.repositorios;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import br.ufrn.imd.meformando.dominio.Equipe;

public class EquipeRepositorio {
	
	@PersistenceContext
	private EntityManager em;
	
	public Equipe adicionar(Equipe equipe) {
		
		if(equipe.getId() == 0)
			em.persist(equipe);
		else
			em.merge(equipe);
		return equipe;
	}
	
	public void remover(Equipe equipe) {
		equipe = em.find(Equipe.class, equipe.getId());
		em.remove(equipe);
	}
	
	@SuppressWarnings("unchecked")
	public List<Equipe> listar() {
		return (List<Equipe>) em.createQuery("select e from Equipe e").getResultList();
	}
	
	public Equipe findEquipeByNome(String nome) {
		try {
			String jpaql ="select e from Equipe e where e.nome = :nome";
			Query q = em.createQuery(jpaql);
			q.setParameter("nome", nome);
			return (Equipe) q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}
