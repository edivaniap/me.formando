package br.ufrn.imd.meformando.repositorios;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.ufrn.imd.meformando.dominio.Tarefa;



@Stateless
public class TerefaRepositorio {
	
	@PersistenceContext
	private EntityManager em;
	
	public Tarefa adicionar(Tarefa tarefa) {
		
		if(tarefa.getId() == 0)
			em.persist(tarefa);
		else
			em.merge(tarefa);
		return tarefa;
	}
	
	public void remover(Tarefa tarefa) {
		tarefa = em.find(Tarefa.class, tarefa.getId());
		em.remove(tarefa);
	}
	
	@SuppressWarnings("unchecked")
	public List<Tarefa> listar() {
		return (List<Tarefa>) em.createQuery("select t from Tarefa t").getResultList();
	}	
	
	//Rever essa busca
	public Tarefa findTarefaByStatus(String status) {
		try {
			String jpaql ="select t from Tarefa t where t.status = :status";
			Query q = em.createQuery(jpaql);
			q.setParameter("status", status);
			return (Tarefa) q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
