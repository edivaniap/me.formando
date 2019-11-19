package br.ufrn.imd.meformando.repositories;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.ufrn.imd.meformando.dominio.Cerimonial;
import br.ufrn.imd.meformando.dominio.Evento;

@Stateless
public class EventoRepository {

	@PersistenceContext
	private EntityManager em;

	public Evento adicionar(Evento eventoComemoracao) {
		if (eventoComemoracao.getId() == 0)
			em.persist(eventoComemoracao);
		else
			em.merge(eventoComemoracao);
		return eventoComemoracao;
	}

	public Evento alterar(Evento eventoComemoracao) {
		em.persist(eventoComemoracao);
		return eventoComemoracao;
	}

	public List<Evento> findTurmaByComissao(Cerimonial cerimonial) {
		return cerimonial.getEventosComemoracoes();
	}

	public void remover(Evento eventoComemoracao) {
		eventoComemoracao = em.find(Evento.class, eventoComemoracao.getId());
		em.remove(eventoComemoracao);
	}

	@SuppressWarnings("unchecked")
	public List<Evento> listar() {
		return (List<Evento>) em.createQuery("select e from Evento e").getResultList();
	}

	public Evento findEventoByTitulo(String titulo) {
		try {
			String jpaql = "select e from Evento e where e.titulo = :titulo";
			Query q = em.createQuery(jpaql);
			q.setParameter("titulo", titulo);
			return (Evento) q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public Evento findEventoById(int id) {
		try {
			String jpaql = "select e from Evento e where e.id = :id";
			Query q = em.createQuery(jpaql);
			q.setParameter("id", id);
			return (Evento) q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public Evento findEventoByTituloByCerimonial(String titulo,  Cerimonial cerimonial) {
		try {
			String jpaql = "SELECT e FROM Evento e WHERE e.cerimonial = :cerimonial AND e.titulo = :titulo";
			Query q = em.createQuery(jpaql);
	
			q.setParameter("cerimonial", cerimonial);
			q.setParameter("titulo", titulo);
			
			return (Evento) q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}