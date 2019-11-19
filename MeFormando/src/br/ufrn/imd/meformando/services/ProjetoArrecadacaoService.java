package br.ufrn.imd.meformando.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import br.ufrn.imd.meformando.dominio.ProjetoArrecadacao;
import br.ufrn.imd.meformando.exceptions.BusinessException;
import br.ufrn.imd.meformando.repositories.ArrecadacaoRepository;



@Stateless
public class ProjetoArrecadacaoService {
	
	@Inject
	private ArrecadacaoRepository projetoArecadacaoRepository;
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ProjetoArrecadacao adicionar(ProjetoArrecadacao projetoArrecadacao) throws BusinessException {

		ProjetoArrecadacao projetoArrecadacaoDB;
		
		// verifica se ha campos vazios
				if (
						projetoArrecadacao.getTitulo().equals("") || 
						projetoArrecadacao.getDataInicial().equals("") || 
						projetoArrecadacao.getDataFinal().equals("") ||
						projetoArrecadacao.getTitulo() == null || 
						projetoArrecadacao.getDataInicial() == null || 
						projetoArrecadacao.getDataFinal() == null
				
					) throw new BusinessException("Todos o campos sao obrigatorios");
				
				//valida custo maior que 0.0
				if(projetoArrecadacao.getCusto() <= 0.0)
					throw new BusinessException("Nao eh possivel adicionar um custo com valor menor que 0 reais!");
				
				//valida custo maior que 0.0
				if(projetoArrecadacao.getGanho() <= 0.0)
					throw new BusinessException("Nao eh possivel adicionar um custo com valor menor que 0 reais!");
				
				//validar dataFinal n�o pode ser antes da dataInicial
				
				projetoArrecadacaoDB = projetoArecadacaoRepository.findArrecadacaoByTituloByTurma(projetoArrecadacao.getTitulo(), projetoArrecadacao.getTurma());
				if(projetoArrecadacaoDB != null) 
					throw new BusinessException("Ja existe projeto de arrecadacao com esse titulo cadastrado");
				
				projetoArecadacaoRepository.adicionar(projetoArrecadacao);
				return projetoArrecadacao;
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void alterar(ProjetoArrecadacao projetoArrecadacao) {
		projetoArecadacaoRepository.alterar(projetoArrecadacao);
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void remover(ProjetoArrecadacao projetoArrecadacao) {
		projetoArecadacaoRepository.remover(projetoArrecadacao);
	}

	
	public List<ProjetoArrecadacao> listar() {
		return projetoArecadacaoRepository.listar();
	}

	
	public ProjetoArrecadacao getProjetoArrecadacao(String titulo) {
		return projetoArecadacaoRepository.findArrecadacaoByTitulo(titulo);
	}
	
	public ProjetoArrecadacao getProjetoArrecadacaoId(int id) {
		return projetoArecadacaoRepository.findArrecadacaoById(id);
	}
}
