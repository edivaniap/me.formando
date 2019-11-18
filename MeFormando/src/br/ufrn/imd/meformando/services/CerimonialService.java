package br.ufrn.imd.meformando.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import br.ufrn.imd.meformando.dominio.Cerimonial;
import br.ufrn.imd.meformando.dominio.Evento;
import br.ufrn.imd.meformando.exceptions.BusinessException;
import br.ufrn.imd.meformando.repositories.CerimonialRepository;
import br.ufrn.imd.meformando.repositories.EventoRepository;
import br.ufrn.imd.meformando.repositories.FormandoRepository;
import br.ufrn.imd.meformando.repositories.TurmaRepository;
import br.ufrn.imd.meformando.util.ValidaCusto;

@Stateless
public class CerimonialService {

	@Inject
	private FormandoRepository formandoRepository;
	
	@Inject
	private TurmaRepository turmaRepository;
	
	@Inject
	private EventoRepository eventoRepository;
	
	@Inject
	private CerimonialRepository cerimonialRepository;
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Cerimonial adicionar(Cerimonial cerimonial) throws BusinessException {

		Cerimonial cerimonialDB;

		// verifica se ha campos vazios
		if (
				cerimonial.getNome().equals("") || 
				cerimonial.getDescricao().equals("") ||
				cerimonial.getNome() == null ||
				cerimonial.getDescricao() == null 				
			) throw new BusinessException("Todos o campos sao obrigatorios");
		
		//valida custo maior que 0.0
		if(cerimonial.getCusto() <= 0.0) {
			throw new BusinessException("Não é possível adicionar um custo com valor menor que 0 reais!");
		}
		
		// valida custo
		if (!ValidaCusto.isCustoValido(cerimonial.getCusto()))
				throw new BusinessException("Não é possivel adicionar custo com letra!");
				
		// valida titulo de cerimonial unico
		cerimonialDB = cerimonialRepository.findCerimonialByNome(cerimonial.getNome());
		if (cerimonialDB != null)
			throw new BusinessException("Ja existe evento com esse titulo cadastrado");
			
		
		cerimonialRepository.adicionar(cerimonial);
		return cerimonial;
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void alterar(Cerimonial cerimonial) {
		//falta regra de negocio
		cerimonialRepository.alterar(cerimonial);
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void remover(Cerimonial cerimonial) {
		cerimonialRepository.remover(cerimonial);
	}
	
	public List<Cerimonial> listar() {
		return cerimonialRepository.listar();
	}
}
