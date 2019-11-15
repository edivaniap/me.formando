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
import br.ufrn.imd.meformando.repositories.CerimonialRepository;
import br.ufrn.imd.meformando.repositories.FormandoRepository;
import br.ufrn.imd.meformando.repositories.TurmaRepository;
import br.ufrn.imd.meformando.util.TokenAuthenticationService;

@Stateless
@Path("/cerimonial")
public class CerimonialController {

	@Inject
	private FormandoRepository formandoRepository;
	
	@Inject
	private CerimonialRepository cerimonialRepository;
	
	@Inject 
	private TurmaRepository turmaRepository;
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/criar")
	public Response criar(@HeaderParam("token") String token,@FormParam("Nome") String nome, @FormParam("Custo") int custo, 
			@FormParam("Descricao") String descricao ) {
		
			String emailFormando = TokenAuthenticationService.getAuthentication(token);
			if (emailFormando == null) {
				return Response.status(202).build();
			}else {
				Formando formando = formandoRepository.findFormandoByEmail(emailFormando);
				Cerimonial cerimonial = new Cerimonial(nome,custo,descricao);
				Turma atualizarTurma = turmaRepository.findTurmaByFormando(formando);
				atualizarTurma.setCerimonial(cerimonial);
				turmaRepository.alterar(atualizarTurma);			
				cerimonialRepository.adicionar(cerimonial);
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
				Formando formando = formandoRepository.findFormandoByEmail(emailFormando);
				
				Turma atualizarTurma = turmaRepository.findTurmaByFormando(formando);
				Cerimonial cerimonial = atualizarTurma.getCerimonial();
				cerimonial.setNome(nome);
				cerimonial.setCusto(custo);
				cerimonial.setDescricao(descricao);
				atualizarTurma.setCerimonial(cerimonial);
				turmaRepository.alterar(atualizarTurma);			
				cerimonialRepository.alterar(cerimonial);
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
			//condi��o caso o token seja inv�lido
			return null;
		}else {
			Formando formando = formandoRepository.findFormandoByEmail(emailFormando);
			Turma turma = turmaRepository.findTurmaByFormando(formando);
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
			//condi��o caso o token seja inv�lido
			return null;
		}else {
			Formando formando = formandoRepository.findFormandoByEmail(emailFormando);
			Turma turma = turmaRepository.findTurmaByFormando(formando);
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

			Formando formando = formandoRepository.findFormandoByEmail(emailFormando);
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
