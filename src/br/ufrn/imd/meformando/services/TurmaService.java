package br.ufrn.imd.meformando.services;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.ufrn.imd.meformando.repositories.TurmaRepository;

@Stateless
public class TurmaService {
	
	@Inject
	private TurmaRepository turmaRepository;

	

}
