package br.ufrn.imd.meformando.repositorios;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.ufrn.imd.meformando.dominio.Opcao;
import br.ufrn.imd.meformando.util.CryptService;

@Stateless
public class OpcaoRepositorio {

	@PersistenceContext
	private EntityManager em;
	
	public Opcao adicionar(Opcao opcao) {
		if(opcao.getId() == 0)
			em.persist(opcao);
		else
			em.merge(opcao);
		return Opcao;
	}
	
	public void remover(Opcao opcao) {
		opcao = em.find(Opcao.class, opcao.getId());
		em.remove(opcao);
	}
	
	@SuppressWarnings("unchecked")
	public List<Opcao> listar() {
		return (List<Opcao>) em.createQuery("select o from Opcao o").getResultList();
	}	

}