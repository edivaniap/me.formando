package br.ufrn.imd.meformando.repositorios;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
	
	public void remover(EventoComemoracao eventoComemoracao) {
		eventoComemoracao = em.find(EventoComemoracao.class, eventoComemoracao.getId());
		em.remove(eventoComemoracao);
	}
	
	@SuppressWarnings("unchecked")
	public List<EventoComemoracao> listar() {
		return (List<EventoComemoracao>) em.createQuery("select e from EventoComemoracao e").getResultList();
	}	

}