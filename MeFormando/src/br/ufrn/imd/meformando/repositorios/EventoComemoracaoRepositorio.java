package br.ufrn.imd.meformando.repositorios;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.ufrn.imd.meformando.dominio.Cerimonial;
import br.ufrn.imd.meformando.dominio.EventoComemoracao;



@Stateless
public class EventoComemoracaoRepositorio {

	@PersistenceContext
	private EntityManager em;
	
	public EventoComemoracao adicionar(EventoComemoracao eventoComemoracao) {
		if(eventoComemoracao.getId() == 0)
			em.persist(eventoComemoracao);
		else
			em.merge(eventoComemoracao);
		return eventoComemoracao;
	}
	
	public EventoComemoracao alterar(EventoComemoracao eventoComemoracao) {
		em.persist(eventoComemoracao);
		return eventoComemoracao;
	}
	
	public List<EventoComemoracao> findTurmaByComissao(Cerimonial cerimonial) {
		return cerimonial.getEventosComemoracoes();
	}
	
	public void remover(EventoComemoracao eventoComemoracao) {
		eventoComemoracao = em.find(EventoComemoracao.class, eventoComemoracao.getId());
		em.remove(eventoComemoracao);
	}
	
	@SuppressWarnings("unchecked")
	public List<EventoComemoracao> listar() {
		return (List<EventoComemoracao>) em.createQuery("select e from EventoComemoracao e").getResultList();
	}	

	public EventoComemoracao findEventoById(int id) {
		try {
			String jpaql ="select f from EventoComemoracao f where f.id = :id";
			Query q = em.createQuery(jpaql);
			q.setParameter("id", id);
			return (EventoComemoracao) q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}