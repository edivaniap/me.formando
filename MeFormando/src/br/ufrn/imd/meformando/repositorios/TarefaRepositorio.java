package br.ufrn.imd.meformando.repositorios;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.ufrn.imd.meformando.dominio.Tarefa;
import br.ufrn.imd.meformando.util.CryptService;

@Stateless
public class TarefaRepositorio {

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

}