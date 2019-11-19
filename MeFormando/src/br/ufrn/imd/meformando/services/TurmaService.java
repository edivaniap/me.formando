package br.ufrn.imd.meformando.services;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.ufrn.imd.meformando.dominio.Evento;
import br.ufrn.imd.meformando.dominio.Formando;
import br.ufrn.imd.meformando.dominio.Turma;
import br.ufrn.imd.meformando.repositories.TurmaRepository;

@Stateless
public class TurmaService {
	
	@Inject
	private TurmaRepository turmaRepository;
	
	public Turma getTurmaId(int id) {
		return turmaRepository.findTurmaById(id);
	}
	
	public Turma getTurma(Formando formando) {
		return turmaRepository.findTurmaByFormando(formando);
	}
	
	
	

}
