package br.ufrn.imd.meformando.repositorios;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.ufrn.imd.meformando.dominio.Pagamento;


@Stateless
public class PagamentoRepositorio {
	
	@PersistenceContext
	private EntityManager em;
	
	public Pagamento adicionar(Pagamento pagamento) {
		
		if(pagamento.getId() == 0)
			em.persist(pagamento);
		else
			em.merge(pagamento);
		return pagamento;
	}
	
	public void remover(Pagamento pagamento) {
		pagamento = em.find(Pagamento.class, pagamento.getId());
		em.remove(pagamento);
	}
	
	@SuppressWarnings("unchecked")
	public List<Pagamento> listar() {
		return (List<Pagamento>) em.createQuery("select p from Pagamento p").getResultList();
	}	
	
	//Ver como vai ficar a busca
//	public Pagamento findPagamentoByStatus(boolean status) {
//		
//	}
}
