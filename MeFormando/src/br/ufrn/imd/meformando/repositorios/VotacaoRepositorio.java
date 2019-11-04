package br.ufrn.imd.meformando.repositorios;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.ufrn.imd.meformando.dominio.Votacao;

@Stateless
public class VotacaoRepositorio {

	@PersistenceContext
	private EntityManager em;
	
	public Votacao adicionar(Votacao votacao) {
		if(votacao.getId() == 0)
			em.persist(votacao);
		else
			em.merge(votacao);
		return votacao;
	}
	
	public void remover(Votacao votacao) {
		votacao = em.find(Votacao.class, votacao.getId());
		em.remove(votacao);
	}
	
	@SuppressWarnings("unchecked")
	public List<Votacao> listar() {
		return (List<Votacao>) em.createQuery("select v from Votacao v").getResultList();
	}	

}