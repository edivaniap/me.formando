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

import br.ufrn.imd.meformando.dominio.Cerimonial;
import br.ufrn.imd.meformando.dominio.EventoComemoracao;
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
	public Response criar(@HeaderParam("token") String token,@FormParam("Nome") String nome, @FormParam("Custo") int custo, 
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
	public Response alterar(@HeaderParam("token") String token,@FormParam("Nome") String nome, @FormParam("Custo") int custo, 
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
	@GET
	@Path("/eventos")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces("application/json; charset=UTF-8")
	public List<Object> eventos(@HeaderParam("token") String token) {
		String emailFormando = TokenAuthenticationService.getAuthentication(token);
		if (emailFormando == null) {
			//condição caso o token seja inválido
			return null;
		}else {
			Formando formando = formandoRepositorio.findFormandoByEmail(emailFormando);
			Turma turma = turmaRepositorio.findTurmaByFormando(formando);
			Cerimonial cerimonial = turma.getCerimonial();
			if(cerimonial != null) {
				List<EventoComemoracao> eventos = cerimonial.getEventosComemoracoes();
				List<Object> eventosDaTurma = new ArrayList<Object>();
				for(int i = 0; i < eventos.size(); i++) {
					EventoComemoracao evento = eventos.get(i);
					eventosDaTurma.add(Arrays.asList(evento.getTitulo(),evento.getDescricao(),evento.getData().toString(),evento.getId()));
				}
				return eventosDaTurma;
			}else {
				return null;
			}
			
			
		}
	}
	
	@GET
	@Path("/cerimonial")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces("application/json; charset=UTF-8")
	public List<Object> cerimonial(@HeaderParam("token") String token) {
		String emailFormando = TokenAuthenticationService.getAuthentication(token);
		if (emailFormando == null) {
			//condição caso o token seja inválido
			return null;
		}else {
			Formando formando = formandoRepositorio.findFormandoByEmail(emailFormando);
			Turma turma = turmaRepositorio.findTurmaByFormando(formando);
			Cerimonial cerimonial = turma.getCerimonial();
			if(cerimonial != null) {
				
				List<Object> cerimonialEnviado = new ArrayList<Object>();							
				cerimonialEnviado.add(Arrays.asList(cerimonial.getNome(),cerimonial.getDescricao(),cerimonial.getCusto()));
				
				return cerimonialEnviado;
			}else {
				return null;
			}
			
			
		}
	}
	
	@GET
	@Path("/temCerimonial")
	public boolean temCerimonial(@HeaderParam("token") String token){

		String emailFormando = TokenAuthenticationService.getAuthentication(token);

		if (emailFormando == null) {

			return false;
		}else {

			Formando formando = formandoRepositorio.findFormandoByEmail(emailFormando);
			if(formando.getTurma() == null) {

				return false;
			}else {

				Turma turma = formando.getTurma();
				Cerimonial cerimonial = turma.getCerimonial();
				if(cerimonial == null) {
					return false;
				}else {

					return true;
				}
			}
			
			
		}
	}
	
}
