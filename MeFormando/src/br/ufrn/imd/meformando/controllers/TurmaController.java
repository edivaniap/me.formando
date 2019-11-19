package br.ufrn.imd.meformando.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.ufrn.imd.meformando.dominio.Formando;
import br.ufrn.imd.meformando.dominio.Turma;
import br.ufrn.imd.meformando.repositories.FormandoRepository;
import br.ufrn.imd.meformando.repositories.TurmaRepository;
import br.ufrn.imd.meformando.util.TokenAuthenticationService;

@Stateless
@Path("/turma")
public class TurmaController {

	@Inject
	private FormandoRepository formandoRepository;

	@Inject
	private TurmaRepository turmaRepository;

	@GET
	@Path("/formandos")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces("application/json; charset=UTF-8")
	public List<Object> formandos(@HeaderParam("token") String token) {
		String emailFormando = TokenAuthenticationService.getAuthentication(token);
		if (emailFormando == null) {
			// condi��o caso o token seja inv�lido
			return null;
		} else {
			Formando formando = formandoRepository.findFormandoByEmail(emailFormando);
			Turma turma = turmaRepository.findTurmaByFormando(formando);
			List<Formando> formandos = turma.getFormandos();
			List<Object> formandosDaTurma = new ArrayList<Object>();
			for (int i = 0; i < formandos.size(); i++) {
				Formando formandoDaTurma = formandos.get(i);
				formandosDaTurma.add(Arrays.asList(formandoDaTurma.getNome(), formandoDaTurma.getEmail(), formandoDaTurma.isComissao()));
			}
			return formandosDaTurma;
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/criar")
	public Response criar(@HeaderParam("token") String token, @FormParam("Titulo") String titulo,
			@FormParam("Instituicao") String instituicao, @FormParam("AnoFormacao") int anoFormacao,
			@FormParam("SemestreFormacao") int semestreFormacao, @FormParam("Curso") String curso) {

		if (token == null || token == "") {
			return Response.status(203).build();
		}
		String emailFormando = TokenAuthenticationService.getAuthentication(token);
		if (emailFormando == null) {
			System.out.print(emailFormando);
			return Response.status(202).build();
		} else {
			Formando formando = formandoRepository.findFormandoByEmail(emailFormando);

			Turma novaTurma = new Turma(titulo, instituicao, anoFormacao, semestreFormacao, curso);
			formando.setComissao(true);
			formando.setTurma(novaTurma);
			formando.setConfirmadoTurma(true);
			formandoRepository.alterar(formando);
			ArrayList<Formando> formandos = new ArrayList<Formando>();
			formandos.add(formando);
			novaTurma.setFormandos(formandos);
			turmaRepository.adicionar(novaTurma);
			return Response.status(201).build();

		}

	}
}
