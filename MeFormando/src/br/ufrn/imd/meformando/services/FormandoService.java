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
import br.ufrn.imd.meformando.exceptions.BusinessException;
import br.ufrn.imd.meformando.repositories.ConviteRepository;
import br.ufrn.imd.meformando.repositories.FormandoRepository;
import br.ufrn.imd.meformando.repositories.TurmaRepository;
import br.ufrn.imd.meformando.util.CryptService;
import br.ufrn.imd.meformando.util.ValidaCPF;
import br.ufrn.imd.meformando.util.ValidaEmail;

@Stateless
public class FormandoService {

	@Inject
	private FormandoRepository formandoRepository;

	@Inject
	private ConviteRepository conviteRepository;

	@Inject
	private TurmaRepository turmaRepository;

	/*! Valida dados de formando enviados por parametro de acordo com as regras de negocio, 
	 * para depois solicitar ao repositorio que o adicione
	 * 
	 * @param formando Objeto que representa o novo Formando a ser inserido
	 * @return O formando inserido
	 * */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Formando adicionar(Formando formando) throws BusinessException {
		
		Formando formandoDB;

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
			) throw new BusinessException("Todos o campos sao obrigatorios");
		
		// valida o cpf e adiciona mascara
		if (!ValidaCPF.isCPF(formando.getCpf()))
			throw new BusinessException("CPF informado invalido");
		else
			formando.setCpf(ValidaCPF.mascararCPF(formando.getCpf()));
		
		// valida formato do email
		if (!ValidaEmail.isEmailValido(formando.getEmail()))
			throw new BusinessException("E-mail informado invalido");
		
		// verifica senha valida
		if(formando.getSenha().length() < 8)
			throw new BusinessException("A senha deve conter no minimo 8 caracteres");
		
		// valida cpf unico
		formandoDB = formandoRepository.findFormandoByCPF(formando.getCpf());
		if (formandoDB != null)
			throw new BusinessException("Ja usuario com este CPF cadastrado");
		
		// valida email unico
		formandoDB = formandoRepository.findFormandoByEmail(formando.getEmail());
		if (formandoDB != null)
			throw new BusinessException("Ja possuimos usuario com este e-mail cadastrado");
		
		formandoRepository.adicionar(formando);
		return formando;
	}
	
	/*!
	 * Solicita ao repositorio que exclua o formando do banco de dados
	 * */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void remover(Formando formando) {
		formandoRepository.remover(formando);
	}

	/*!
	 * Solicita ao repositorio todos os formandos salvos no banco de dados
	 * */
	public List<Formando> listar() {
		return formandoRepository.listar();
	}

	/*!
	 * Solicita ao repositorio um formando com determinado email salvo no banco de dados
	 * 
	 * @param email Chave da busca do formando
	 * @return Formando encontrado, null caso contrario
	 * */
	public Formando getFormando(String email) {
		return formandoRepository.findFormandoByEmail(email);
	}

	/*!
	 * Verifica se ha formando com email e senha informado no banco de dados
	 * Solicita ao repositorio um formando com determinado email salvo no banco de dados
	 * 
	 * @param email Primeira chave da busca do formando
	 * @param senha Segunda chave da busca do formando
	 * @return true se email e senhas sao validas, false se nao
	 * */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean logar(String email, String senha) throws BusinessException {

		Formando formandoDB = formandoRepository.findFormandoByEmail(email);

		if (formandoDB == null)
			throw new BusinessException("Este e-email nao esta cadastrado no nosso sistema");
		else if (!CryptService.verifyPasswords(senha, formandoDB.getSenha()))
			throw new BusinessException("Senha incorreta");

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
