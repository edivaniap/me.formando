package br.ufrn.imd.meformando.repositories;

import java.util.List;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import br.ufrn.imd.meformando.dominio.ProjetoArrecadacao;
import br.ufrn.imd.meformando.dominio.Turma;

@Stateless
public class ArrecadacaoRepository {

	@PersistenceContext
	private EntityManager em;
	
	public ProjetoArrecadacao adicionar(ProjetoArrecadacao projetoArrecadacao) {
		if(projetoArrecadacao.getId() == 0)
			em.persist(projetoArrecadacao);
		else
			em.merge(projetoArrecadacao);
		return projetoArrecadacao;
	}
	
	public void remover(ProjetoArrecadacao projetoArrecadacao) {
		projetoArrecadacao = em.find(ProjetoArrecadacao.class, projetoArrecadacao.getId());
		em.remove(projetoArrecadacao);
	}
	
	public ProjetoArrecadacao alterar(ProjetoArrecadacao projetoArrecadacao) {
		em.persist(projetoArrecadacao);
		return projetoArrecadacao;
	}
	
	@SuppressWarnings("unchecked")
	public List<ProjetoArrecadacao> listar() {
		return (List<ProjetoArrecadacao>) em.createQuery("select pa from ProjetoArrecadacao pa").getResultList();
	}	
	
	public ProjetoArrecadacao findArrecadacaoByTitulo(String titulo) {
		try {
			String jpaql = "select p from ProjetoArrecadacao p where p.titulo = :titulo";
			Query q = em.createQuery(jpaql);
			q.setParameter("titulo", titulo);
			return (ProjetoArrecadacao) q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public ProjetoArrecadacao findArrecadacaoById(int id) {
		try {
			String jpaql = "select p from ProjetoArrecadacao p where p.id = :id";
			Query q = em.createQuery(jpaql);
			q.setParameter("id", id);
			return (ProjetoArrecadacao) q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	
	public ProjetoArrecadacao findArrecadacaoByTituloByTurma(String titulo,  Turma turma) {
		try {
			String jpaql = "SELECT p FROM ProjetoArrecadacao p WHERE p.turma = :turma AND p.titulo = :titulo";
			Query q = em.createQuery(jpaql);
	
			q.setParameter("turma", turma);
			q.setParameter("titulo", titulo);
			
			return (ProjetoArrecadacao) q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}