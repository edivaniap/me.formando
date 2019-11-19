package br.ufrn.imd.meformando.repositories;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.ufrn.imd.meformando.dominio.Formando;
import br.ufrn.imd.meformando.dominio.Mensalidade;

@Stateless
public class MensalidadeRepository {

	@PersistenceContext
	private EntityManager em;
	
	public Mensalidade adicionar(Mensalidade mensalidade) {
		if(mensalidade.getId() == 0)
			em.persist(mensalidade);
		else
			em.merge(mensalidade);
		return mensalidade;
	}
	
	public void remover(Mensalidade mensalidade) {
		mensalidade = em.find(Mensalidade.class, mensalidade.getId());
		em.remove(mensalidade);
	}
	
	@SuppressWarnings("unchecked")
	public List<Mensalidade> listar() {
		return (List<Mensalidade>) em.createQuery("select m from Mensalidade m").getResultList();
	}	
	
	public List<Mensalidade> findMensalidadeByFormando(Formando formando){
		try {
			String queryString = "select m from Mensalidade m where m.formando = :formando";
			Query q = em.createQuery(queryString);
			q.setParameter("formando", formando);
			return (List<Mensalidade>) q.getResultList();
		}catch (NoResultException e) {
				return null;
			}
	}

	public Mensalidade findMensalidadeByFormandoAndMes(Formando formando, Date mes) {
		try {
			String queryString = "select m from Mensalidade m where m.formando = :formando and m.mes = :mes";
			Query q = em.createQuery(queryString);
			q.setParameter("formando", formando);
			q.setParameter("mes", mes);
			return (Mensalidade) q.getSingleResult();
		}catch (NoResultException e) {
			return null;
		}
	}

}