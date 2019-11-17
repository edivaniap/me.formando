package br.ufrn.imd.meformando.services;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import br.ufrn.imd.meformando.dominio.Evento;
import br.ufrn.imd.meformando.dominio.Formando;
import br.ufrn.imd.meformando.dominio.Turma;
import br.ufrn.imd.meformando.exceptions.BusinessException;
import br.ufrn.imd.meformando.repositories.EventoRepository;
import br.ufrn.imd.meformando.repositories.FormandoRepository;
import br.ufrn.imd.meformando.repositories.TurmaRepository;
import br.ufrn.imd.meformando.util.ValidaCPF;
import br.ufrn.imd.meformando.util.ValidaData;
import br.ufrn.imd.meformando.util.ValidaEmail;

@Stateless
public class EventoService {
	
	@Inject
	private FormandoRepository formandoRepository;
	
	@Inject
	private TurmaRepository turmaRepository;
	
	@Inject
	private EventoRepository eventoRepository;
	
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Evento adicionar(Evento evento) throws BusinessException {

		Evento eventoDB;

		// verifica se ha campos vazios
		if (
				evento.getTitulo().equals("") || 
				//evento.getCusto() == 0.0 || 
				evento.getData().equals("") || 
				evento.getDescricao().equals("") ||
				evento.getTitulo() == null ||
				//evento.getCusto() == null || 
				evento.getData() == null || 
				evento.getDescricao() == null 				
			) throw new BusinessException("Todos o campos sao obrigatorios");
		
		//valida custo maior que 0.0
		if(evento.getCusto() <= 0.0) {
			throw new BusinessException("Não é possível adicionar um custo com valor menor que 0 reais!");
		}
				
		// valida titulo de evento unico
		eventoDB = eventoRepository.findEventoByTituloByCerimonial(evento.getTitulo(), evento.getCerimonial());
		if (eventoDB != null)
			throw new BusinessException("Ja existe evento com esse titulo cadastrado");
		
		
		
		eventoRepository.adicionar(evento);
		return evento;
	}

}
