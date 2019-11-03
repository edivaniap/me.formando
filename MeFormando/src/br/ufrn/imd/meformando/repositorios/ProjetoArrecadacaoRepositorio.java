package br.ufrn.imd.meformando.repositorios;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.ufrn.imd.meformando.dominio.ProjetoArrecadacao;


@Stateless
public class ProjetoArrecadacaoRepositorio {
	
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
		return (List<ProjetoArrecadacao>) em.createQuery("select p from ProjetoArrecadacao p").getResultList();
	}	
	
	public ProjetoArrecadacao findProjetoArrecadacaoByTitulo(String titulo) {
		try {
			String jpaql ="select p from ProjetoArrecadacao p where p.titulo = :titulo";
			Query q = em.createQuery(jpaql);
			q.setParameter("titulo", titulo);
			return (ProjetoArrecadacao) q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}
