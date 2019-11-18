package br.ufrn.imd.meformando.services;

import java.util.List;

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
import br.ufrn.imd.meformando.util.ValidaCusto;
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
		
		// valida custo
		if (!ValidaCusto.isCustoValido(evento.getCusto()))
				throw new BusinessException("Não é possivel adicionar custo com letra!");
				
		// valida titulo de evento unico
		eventoDB = eventoRepository.findEventoByTituloByCerimonial(evento.getTitulo(), evento.getCerimonial());
		if (eventoDB != null)
			throw new BusinessException("Ja existe evento com esse titulo cadastrado");
		
		
		
		eventoRepository.adicionar(evento);
		return evento;
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void alterar(Evento evento) {
		eventoRepository.alterar(evento);
	}
	
	/*!
	 * Solicita ao repositorio que exclua o evento do banco de dados
	 * */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void remover(Evento evento) {
		eventoRepository.remover(evento);
	}

	/*!
	 * Solicita ao repositorio todos os eventos salvos no banco de dados
	 * */
	public List<Evento> listar() {
		return eventoRepository.listar();
	}

	/*!
	 * Solicita ao repositorio um evento com determinado titulo salvo no banco de dados
	 * 
	 * @param titulo Chave da busca do evento
	 * @return Evento encontrado, null caso contrario
	 * */
	public Evento getEvento(String titulo) {
		return eventoRepository.findEventoByTitulo(titulo);
	}
	
	public Evento getEventoId(int id) {
		return eventoRepository.findEventoById(id);
	}

}
