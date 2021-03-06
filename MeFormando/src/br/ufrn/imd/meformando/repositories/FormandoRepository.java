package br.ufrn.imd.meformando.repositories;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.ufrn.imd.meformando.dominio.Formando;
import br.ufrn.imd.meformando.util.CryptService;

@Stateless
public class FormandoRepository {

	@PersistenceContext
	private EntityManager em;
	
	
	public Formando adicionar(Formando formando) {
		formando.setSenha(CryptService.getHashedPassword(formando.getSenha()));
		
		if(formando.getId() == 0)
			em.persist(formando);
		else
			em.merge(formando);
		return formando;
	}
	

	public Formando alterar(Formando formando) {
		em.persist(formando);
		return formando;
	}
	
	public void remover(Formando formando) {
		formando = em.find(Formando.class, formando.getId());
		em.remove(formando);
	}
	
	@SuppressWarnings("unchecked")
	public List<Formando> listar() {
		return (List<Formando>) em.createQuery("select f from Formando f").getResultList();
	}	
	
	public Formando findFormandoByEmail(String email) {
		try {
			String jpaql ="select f from Formando f where f.email = :email";
			Query q = em.createQuery(jpaql);
			q.setParameter("email", email);
			return (Formando) q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public Formando findFormandoByCPF(String cpf) {
		try {
			String jpaql ="select f from Formando f where f.cpf = :cpf";
			Query q = em.createQuery(jpaql);
			q.setParameter("cpf", cpf);
			return (Formando) q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
