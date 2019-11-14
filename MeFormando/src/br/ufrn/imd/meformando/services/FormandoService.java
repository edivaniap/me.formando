package br.ufrn.imd.meformando.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import br.ufrn.imd.meformando.dominio.Convite;
import br.ufrn.imd.meformando.dominio.Formando;
import br.ufrn.imd.meformando.dominio.Turma;
import br.ufrn.imd.meformando.exceptions.NegocioException;
import br.ufrn.imd.meformando.repositorios.ConviteRepositorio;
import br.ufrn.imd.meformando.repositorios.FormandoRepositorio;
import br.ufrn.imd.meformando.repositorios.TurmaRepositorio;
import br.ufrn.imd.meformando.util.CryptService;
import br.ufrn.imd.meformando.util.ValidaCPF;
import br.ufrn.imd.meformando.util.ValidaEmail;

@Stateless
public class FormandoService {

	@Inject
	private FormandoRepositorio formandoRepository;

	@Inject
	private ConviteRepositorio conviteRepository;

	@Inject
	private TurmaRepositorio turmaRepository;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Formando adicionar(Formando formando) throws NegocioException {
		// verificar se o formando com email passado existe
		Formando formandoDB = formandoRepository.findFormandoByEmail(formando.getEmail());

		// verifica se ha campos vazios
		if (
				formando.getNome().equals("") || 
				formando.getCpf().equals("") || 
				formando.getEmail().equals("") || 
				formando.getSenha().equals("") || 
				formando.getNome() == null || 
				formando.getCpf() == null || 
				formando.getEmail() == null || 
				formando.getSenha() == null
			)
			throw new NegocioException("Todos o campos sao obrigatorios");
		
		// verifica senha valida
		if(formando.getSenha().length() < 8)
			throw new NegocioException("A senha deve conter no minimo 8 caracteres");
		
		// valida email unico
		if (formandoDB != null)
			throw new NegocioException("Ja possuimos este e-mail cadastrado no sistema");

		// valida formato do email
		if (!ValidaEmail.isEmailValido(formando.getEmail()))
			throw new NegocioException("E-mail informado invalido");

		// valida o cpf
		if (!ValidaCPF.isCPF(formando.getCpf()))
			throw new NegocioException("CPF informado invalido");

		formando.setCpf(ValidaCPF.mascararCPF(formando.getCpf()));
		formandoRepository.adicionar(formando);
		return formando;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void remover(Formando formando) {
		formandoRepository.remover(formando);
	}

	public List<Formando> listar() {
		return formandoRepository.listar();
	}

	public Formando getFormando(String email) {
		return formandoRepository.findFormandoByEmail(email);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean logar(String email, String senha) throws NegocioException {

		Formando formandoDB = formandoRepository.findFormandoByEmail(email);

		if (formandoDB == null)
			throw new NegocioException("Este e-email nao esta cadastrado no nosso sistema");
		else if (!CryptService.verifyPasswords(senha, formandoDB.getSenha()))
			throw new NegocioException("Senha incorreta");

		return CryptService.verifyPasswords(senha, formandoDB.getSenha());
	}

	/*
	 * @TransactionAttribute(TransactionAttributeType.REQUIRED) public void
	 * aceitarConvite(int id_turma, int id_convite) throws NegocioException { Turma
	 * turma = turmaRepository.findTurmaById(id_turma); if(turma != null) { Formando
	 * formando = formandoRepositorio.findFormandoByEmail(emailFormando);
	 * formando.setConfirmadoTurma(true); formando.setTurma(turma);
	 * formandoRepositorio.alterar(formando);
	 * 
	 * Convite convite = conviteRepositorio.findConviteById(idConvite);
	 * conviteRepositorio.remover(convite); return Response.status(201).build(); } }
	 */
}
