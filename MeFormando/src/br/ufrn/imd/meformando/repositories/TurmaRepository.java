package br.ufrn.imd.meformando.repositories;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.ufrn.imd.meformando.dominio.Formando;
import br.ufrn.imd.meformando.dominio.Turma;


@Stateless
public class TurmaRepository {
	
	@PersistenceContext
	private EntityManager em;
	
	public Turma adicionar(Turma turma) {
		
		if(turma.getId() == 0)
			em.persist(turma);
		else
			em.merge(turma);
		return turma;
	}

	public Turma alterar(Turma turma) {				
		em.persist(turma);		
		return turma;
	}
	
	public Turma findTurmaByFormando(Formando formando) {
		return formando.getTurma();
	}
	
	public Turma findTurmaById(int id) {
		try {
			String jpaql ="select f from Turma f where f.id = :id";
			Query q = em.createQuery(jpaql);
			q.setParameter("id", id);
			return (Turma) q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public Turma findTurmaByTitulo(String titulo) {
		try {
			String jpaql ="select t from Turma t where t.titulo = :titulo";
			Query q = em.createQuery(jpaql);
			q.setParameter("titulo", titulo);
			return (Turma) q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}

