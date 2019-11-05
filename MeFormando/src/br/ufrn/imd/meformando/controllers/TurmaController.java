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
import br.ufrn.imd.meformando.repositorios.FormandoRepositorio;
import br.ufrn.imd.meformando.repositorios.TurmaRepositorio;
import br.ufrn.imd.meformando.util.TokenAuthenticationService;

@Stateless
@Path("/turma")
public class TurmaController {
	
	@Inject
	private FormandoRepositorio formandoRepositorio;
	
	@Inject 
	private TurmaRepositorio turmaRepositorio;
	
	@GET
	@Path("/turma")
	@Produces("application/json; charset=UTF-8")
	public List<Object> turmas(@HeaderParam("token") String token) {
		String emailFormando = TokenAuthenticationService.getAuthentication(token);
		if (emailFormando == null) {
			//condição caso o token seja inválido
			return null;
		}else {
			Formando formando = formandoRepositorio.findFormandoByEmail(emailFormando);
			Turma turma = turmaRepositorio.findTurmaByFormando(formando);
			return Arrays.asList(formando.isConfirmadoTurma(), turma);
		}
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/criar")
	public Response criar(@FormParam("token") String token,@FormParam("Titulo") String titulo, @FormParam("Instituicao") String instituicao, 
			@FormParam("AnoFormacao") int anoFormacao, 
			@FormParam("SemestreFormacao") int semestreFormacao ,@FormParam("Curso") String curso) {
			String emailFormando = TokenAuthenticationService.getAuthentication(token);
			if (emailFormando == null) {
				return Response.status(202).build();
			}else {
				Formando formando = formandoRepositorio.findFormandoByEmail(emailFormando);
				Turma novaTurma = new Turma(titulo,instituicao,anoFormacao,semestreFormacao,curso);
				formando.setComissao(true);
				formando.setTurma(novaTurma);
				formando.setConfirmadoTurma(true);
				
				formandoRepositorio.alterar(formando);
				ArrayList<Formando> formandos = new ArrayList<Formando>();
				formandos.add(formando);
				novaTurma.setFormandos(formandos);
				novaTurma.setQtdFormandos(1);
				turmaRepositorio.adicionar(novaTurma);
				return Response.status(201).build();
			
			}

	}
}
