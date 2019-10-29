package br.ufrn.imd.meformando.controllers;

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
import javax.ws.rs.core.Response.ResponseBuilder;

import br.ufrn.imd.meformando.dominio.Formando;
import br.ufrn.imd.meformando.repositorios.FormandoRepositorio;
import br.ufrn.imd.meformando.util.CryptService;
import br.ufrn.imd.meformando.util.TokenAuthenticationService;

@Stateless
@Path("/usuario")
public class FormandoController {
	
	@Inject
	private FormandoRepositorio formandoRepositorio;
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces
	@Path("/logar")
	public Response logar(@FormParam("email") String email, 
			@FormParam("senha") String senha) {
		System.out.println(senha);
		Formando formandoLogado = formandoRepositorio.findFormandoByEmail(email);
		if (formandoLogado != null && CryptService.verifyPasswords(senha, formandoLogado.getSenha())) {
			String token = TokenAuthenticationService.addAuthentication(email);
			return Response.status(201).header("token", token).build();
		}else {
			return Response.status(202).header("erro", "Senha ou Email Inválidos").build();
		}
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/registrar")
	public Response registar(@FormParam("email") String email, 
			@FormParam("senha") String senha) {
		Formando novoFormando = new Formando();
		novoFormando.setEmail(email);
		novoFormando.setSenha(senha);
		if (formandoRepositorio.findFormandoByEmail(novoFormando.getEmail()) != null) {
			return Response.status(202).header("erro", "Usuário Existente").build();
		}else {
			formandoRepositorio.adicionar(novoFormando);
		}
		return Response.status(201).build();
	}
}