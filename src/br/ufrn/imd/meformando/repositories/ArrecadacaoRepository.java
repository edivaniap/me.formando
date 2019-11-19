package br.ufrn.imd.meformando.repositories;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.ufrn.imd.meformando.dominio.ProjetoArrecadacao;

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
	
	@SuppressWarnings("unchecked")
	public List<ProjetoArrecadacao> listar() {
		return (List<ProjetoArrecadacao>) em.createQuery("select pa from ProjetoArrecadacao pa").getResultList();
	}	

}