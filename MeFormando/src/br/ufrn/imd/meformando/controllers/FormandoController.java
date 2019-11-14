package br.ufrn.imd.meformando.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ejb.EJB;
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

import br.ufrn.imd.meformando.dominio.Convite;
import br.ufrn.imd.meformando.dominio.Formando;
import br.ufrn.imd.meformando.dominio.Turma;
import br.ufrn.imd.meformando.exceptions.NegocioException;
import br.ufrn.imd.meformando.repositorios.ConviteRepositorio;
import br.ufrn.imd.meformando.repositorios.FormandoRepositorio;
import br.ufrn.imd.meformando.repositorios.TurmaRepositorio;
import br.ufrn.imd.meformando.services.FormandoService;
import br.ufrn.imd.meformando.util.CryptService;
import br.ufrn.imd.meformando.util.TokenAuthenticationService;

@Stateless
@Path("/usuario")
public class FormandoController {
	
	@Inject
	private FormandoRepositorio formandoRepositorio;
	
	@Inject 
	private TurmaRepositorio turmaRepositorio;
	
	@Inject 
	private ConviteRepositorio conviteRepositorio;
	
	@EJB
	private FormandoService formandoService;
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces
	@Path("/logar")
	public Response logar(@FormParam("email") String email, @FormParam("senha") String senha) {
		String token = "";

		try {
			if(formandoService.logar(email, senha))
				token = TokenAuthenticationService.addAuthentication(email);
		} catch (NegocioException e) {
			return Response.status(202).header("erro", "Erro: " + e.getMessage()).build();
		}
		
		return Response.status(201).header("token", token).build();	
	}
	
	
	
	
	/* USANDO O SERVICE */
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/registrar")
	public Response registrar(
			@FormParam("nome") String nome,
			@FormParam("cpf") String cpf, 
			@FormParam("email") String email, 
			@FormParam("senha") String senha
			) {
		
		Formando novoFormando = new Formando(nome, cpf, email, senha, false);
		
		try {
			formandoService.adicionar(novoFormando);
		} catch (NegocioException e) {
			return Response.status(202).header("erro", "Erro: " + e.getMessage()).build();
		}
		
		return Response.status(201).build();
	}
	
	//FAZENDO ESSE
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/aceitarConvite")
	public Response aceitarConvite(@HeaderParam("token") String token,@FormParam("id") int id,@FormParam("idConvite") int idConvite) {
		String emailFormando = TokenAuthenticationService.getAuthentication(token);
		if (emailFormando == null) {
			//condi��o caso o token seja inv�lido
			return null;
		}else {
			Turma turma = turmaRepositorio.findTurmaById(id);
			if(turma != null) {
				Formando formando = formandoRepositorio.findFormandoByEmail(emailFormando);
				formando.setConfirmadoTurma(true);
				formando.setTurma(turma);
				formandoRepositorio.alterar(formando);
				
				Convite convite = conviteRepositorio.findConviteById(idConvite);
				conviteRepositorio.remover(convite);
				return Response.status(201).build();
			}
	
			return null;	
		}
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/recusarConvite")
	public Response recusarConvite(@HeaderParam("token") String token,@FormParam("id") int id,@FormParam("idConvite") int idConvite) {
		String emailFormando = TokenAuthenticationService.getAuthentication(token);
		if (emailFormando == null) {
			//condi��o caso o token seja inv�lido
			return null;
		}else {
			Turma turma = turmaRepositorio.findTurmaById(id);
			if(turma != null) {
				Convite convite = conviteRepositorio.findConviteById(idConvite);
				conviteRepositorio.remover(convite);
				return Response.status(201).build();
			}
			
			
			
			
			return null;
			
			
		}
	}
	
	
	//usando service
	@GET
	@Path("/confirmadoTurma")
	public boolean isConfirmadoTurma(@HeaderParam("token") String token){

		String emailAutenticado = TokenAuthenticationService.getAuthentication(token);
		
		if (emailAutenticado == null) {
			return false;
		}
		
		Formando formandoLogado = formandoService.getFormando(emailAutenticado);
		return formandoLogado.isConfirmadoTurma();
	}
	
	//usando service
	
	@GET
	@Path("/confirmadoComissao")
	public boolean isComissao(@HeaderParam("token") String token){

		String emailAutenticado = TokenAuthenticationService.getAuthentication(token);
		
		if (emailAutenticado == null) {
			return false;
		}
		
		Formando formandoLogado = formandoService.getFormando(emailAutenticado);
		return formandoLogado.isComissao();
	}
	
	//usando service (medio) - mas ta mt confuso, precisa revisar
	
	@GET
	@Path("/convites")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces("application/json; charset=UTF-8")
	public List<Object> convites(@HeaderParam("token") String token) {
		
		String emailAutenticado = TokenAuthenticationService.getAuthentication(token);
		
		if (emailAutenticado == null) {
			return null;
		} else {
			Formando formandoLogado = formandoService.getFormando(emailAutenticado);
			
			List<Convite> convites = conviteRepositorio.findConviteByFormando(formandoLogado.getId());
			List<Object> convitesDoFormando = new ArrayList<Object>();
			for(int i = 0 ; i < convites.size(); i++) {
				Convite convite = convites.get(i);
				Turma turma = turmaRepositorio.findTurmaById(convite.getIdDaTurma());
				convitesDoFormando.add(Arrays.asList(turma.getTitulo(),convite.getFormandoQueConvidou(),turma.getId(),convite.getId()));
				
			}
			
			
			return convitesDoFormando;
			
			
		}
	}
}