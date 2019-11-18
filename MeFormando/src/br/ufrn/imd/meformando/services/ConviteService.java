package br.ufrn.imd.meformando.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import br.ufrn.imd.meformando.dominio.Convite;
import br.ufrn.imd.meformando.dominio.Formando;
import br.ufrn.imd.meformando.dominio.Turma;
import br.ufrn.imd.meformando.exceptions.BusinessException;
import br.ufrn.imd.meformando.repositories.ConviteRepository;
import br.ufrn.imd.meformando.repositories.FormandoRepository;
import br.ufrn.imd.meformando.repositories.TurmaRepository;

@Stateless
public class ConviteService {

	@Inject
	private ConviteRepository conviteRepository;

	@Inject
	private FormandoRepository formandoRepository;

	@Inject
	private TurmaRepository turmaRepository;

	/*
	 * ! Solicita ao repositorio e retorna lista de convites buscados atravez do
	 * email do convidado
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Object> listarPorConvidado(String email_convidado) throws BusinessException {

		Formando convidado = formandoRepository.findFormandoByEmail(email_convidado);
		List<Object> convitesDoFormando = new ArrayList<Object>();

		if (convidado != null) {
			List<Convite> convites = conviteRepository.findConviteByFormando(convidado.getId());
			if (convites != null) {
				for (Convite convite : convites) {
					Turma turma = turmaRepository.findTurmaById(convite.getIdDaTurma());

					convitesDoFormando.add(Arrays.asList(turma.getTitulo(), convite.getFormandoQueConvidou(),
							turma.getId(), convite.getId()));
				}
			} else {
				throw new BusinessException("Nao existe convite para este formando");
			}
		} else {
			throw new BusinessException("Formando com email " + email_convidado + " nao existe");
		}

		return convitesDoFormando;
	}

	/*
	 * ! Seta status de convite para Aceito e adiciona turma ao formando
	 * 
	 * @param email_formando Email do formando que recebeu o convite
	 * 
	 * @param id_turma ID da turma que convidou o formando
	 * 
	 * @param id_convite ID do convite enviado
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void aceitar(String email_formando, int id_turma, int id_convite) throws BusinessException {
		Formando formando = formandoRepository.findFormandoByEmail(email_formando);
		Convite convite = conviteRepository.findConviteById(id_convite);
		Turma turma = turmaRepository.findTurmaById(id_turma);

		if (formando != null) {
			if (turma != null) {
				if (convite != null) {
					if (formando.getTurma() == null) {

						formando.setConfirmadoTurma(true);
						formando.setTurma(turma);
						formandoRepository.alterar(formando);

						convite.setStatus("Aceito");
						conviteRepository.alterar(convite);

					} else {
						throw new BusinessException("Formando com email " + email_formando + " ja esta em uma turma");
					}
				} else {
					throw new BusinessException("Convite de ID " + id_convite + " nao existe");
				}
			} else {
				throw new BusinessException("Turma de ID " + id_turma + " nao existe");
			}
		} else {
			throw new BusinessException("Formando com email " + email_formando + " nao existe");
		}
	}

	/* ! Seta status de convite para Recusado */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void recusar(String email_formando, int id_convite) throws BusinessException {
		Formando formando = formandoRepository.findFormandoByEmail(email_formando);
		Convite convite = conviteRepository.findConviteById(id_convite);

		if (formando != null) {
			if (convite != null) {

				convite.setStatus("Recusado");
				conviteRepository.alterar(convite);
			} else {
				throw new BusinessException("Convite de ID " + id_convite + " nao existe");
			}
		} else {
			throw new BusinessException("Formando com email " + email_formando + " nao existe");
		}
	}

	/* ! Convida formando para turma */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void convidar(String email_convidado, String email_convidante) throws BusinessException {
		Formando formandoConvidado = formandoRepository.findFormandoByEmail(email_convidado);
		if (formandoConvidado != null) {
			if (formandoConvidado.getTurma() == null) {
				Formando formando = formandoRepository.findFormandoByEmail(email_convidante);

				List<Convite> convites = formandoConvidado.getConvites();

				for (Convite c : convites) {
					if (c.getIdDaTurma() == formando.getTurma().getId())
						throw new BusinessException(email_convidado + " ja foi convidado para esta turma");
				}
				
				Convite convite = new Convite(formando.getTurma().getId(), formando.getNome(), formandoConvidado);
				convites.add(convite);
				formandoConvidado.setConvites(convites);
				formandoRepository.alterar(formandoConvidado);
			} else {
				throw new BusinessException(email_convidado + " ja esta em uma turma");
			}
		} else {
			throw new BusinessException("Formando com email " + email_convidado + " nao existe");
		}
	}
}
