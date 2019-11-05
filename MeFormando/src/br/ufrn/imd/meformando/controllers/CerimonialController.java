package br.ufrn.imd.meformando.controllers;


import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.ufrn.imd.meformando.dominio.Cerimonial;
import br.ufrn.imd.meformando.dominio.Formando;
import br.ufrn.imd.meformando.dominio.Turma;
import br.ufrn.imd.meformando.repositorios.CerimonialRepositorio;
import br.ufrn.imd.meformando.repositorios.FormandoRepositorio;
import br.ufrn.imd.meformando.repositorios.TurmaRepositorio;
import br.ufrn.imd.meformando.util.TokenAuthenticationService;

@Stateless
@Path("/cerimonial")
public class CerimonialController {

	@Inject
	private FormandoRepositorio formandoRepositorio;
	
	@Inject
	private CerimonialRepositorio cerimonialRepositorio;
	
	@Inject 
	private TurmaRepositorio turmaRepositorio;
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/criar")
	public Response criar(@FormParam("token") String token,@FormParam("Nome") String nome, @FormParam("Custo") int custo, 
			@FormParam("Descricao") String descricao ) {
		
			String emailFormando = TokenAuthenticationService.getAuthentication(token);
			if (emailFormando == null) {
				return Response.status(202).build();
			}else {
				Formando formando = formandoRepositorio.findFormandoByEmail(emailFormando);
				Cerimonial cerimonial = new Cerimonial(nome,custo,descricao);
				Turma atualizarTurma = turmaRepositorio.findTurmaByFormando(formando);
				atualizarTurma.setCerimonial(cerimonial);
				turmaRepositorio.alterar(atualizarTurma);			
				cerimonialRepositorio.adicionar(cerimonial);
				return Response.status(201).build();
			
			}

	}
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/alterar")
	public Response alterar(@FormParam("token") String token,@FormParam("Nome") String nome, @FormParam("Custo") int custo, 
			@FormParam("Descricao") String descricao ) {
		
			String emailFormando = TokenAuthenticationService.getAuthentication(token);
			if (emailFormando == null) {
				return Response.status(202).build();
			}else {
				Formando formando = formandoRepositorio.findFormandoByEmail(emailFormando);
				
				Turma atualizarTurma = turmaRepositorio.findTurmaByFormando(formando);
				Cerimonial cerimonial = atualizarTurma.getCerimonial();
				cerimonial.setNome(nome);
				cerimonial.setCusto(custo);
				cerimonial.setDescricao(descricao);
				atualizarTurma.setCerimonial(cerimonial);
				turmaRepositorio.alterar(atualizarTurma);			
				cerimonialRepositorio.alterar(cerimonial);
				return Response.status(201).build();
			
			}

	}
}
