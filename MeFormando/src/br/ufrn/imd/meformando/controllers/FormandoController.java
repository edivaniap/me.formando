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
import br.ufrn.imd.meformando.exceptions.BusinessException;
import br.ufrn.imd.meformando.repositories.ConviteRepository;
import br.ufrn.imd.meformando.repositories.TurmaRepository;
import br.ufrn.imd.meformando.services.FormandoService;
import br.ufrn.imd.meformando.util.TokenAuthenticationService;

@Stateless
@Path("/usuario")
public class FormandoController {
		
	@Inject 
	private TurmaRepository turmaRepository;
	
	@Inject 
	private ConviteRepository conviteRepository;
	
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
		} catch (BusinessException e) {
			return Response.status(202).header("erro", e.getMessage()).build();
		}
		
		return Response.status(201).header("token", token).build();	
	}

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/registrar")
	public Response registrar(
			@FormParam("nome") String nome,
			@FormParam("cpf") String cpf, 
			@FormParam("email") String email, 
			@FormParam("senha") String senha
			) {
				
		try {
			formandoService.adicionar(new Formando(nome, cpf, email, senha, false));
		} catch (BusinessException e) {
			return Response.status(202).header("erro", e.getMessage()).build();
		}
		
		return Response.status(201).build();
	}

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
	
	/*da erro quando acesso a lista
	@GET
	@Path("/listaJSON")
	@Produces("application/json; charset=UTF-8")
	public List<Formando> listar() {
		return formandoService.listar();
	}
	*/
}