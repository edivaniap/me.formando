package br.ufrn.imd.meformando.services;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import br.ufrn.imd.meformando.dominio.Formando;
import br.ufrn.imd.meformando.dominio.Mensalidade;
import br.ufrn.imd.meformando.dominio.Turma;
import br.ufrn.imd.meformando.repositories.MensalidadeRepository;

@Stateless
public class PagamentoService {

	@Inject
	private MensalidadeRepository mensalidadeRepository;
	
	public List<Mensalidade> mensalidades(Formando formando){
		return mensalidadeRepository.findMensalidadeByFormando(formando);
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void cadastrarMensalidade(Turma turma, Date mes, double valor) {
		
		List<Formando> formandos = turma.getFormandos();
		
		for (Formando formando: formandos) {
			Mensalidade mensalidade = mensalidadeRepository.findMensalidadeByFormandoAndMes(formando, mes);
			if (mensalidade == null) {
				Mensalidade novaMensalidade = new Mensalidade(valor, mes, false, formando);
				mensalidadeRepository.adicionar(novaMensalidade);
			}
		}
		
	}
	
}
