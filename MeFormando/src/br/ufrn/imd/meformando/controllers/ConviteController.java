package br.ufrn.imd.meformando.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
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
import br.ufrn.imd.meformando.services.ConviteService;
import br.ufrn.imd.meformando.util.TokenAuthenticationService;

@Stateless
@Path("/convites")
public class ConviteController {
	
	@EJB
	private ConviteService conviteService;
	
	/* USANDO O SERVICE */
	// em andamento. e esta mt confuso, precisa revisar classe convite
	@GET
	@Path("/por_convidado")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces("application/json; charset=UTF-8")
	public List<Object> listar(@HeaderParam("token") String token) {

		String emailAutenticado = TokenAuthenticationService.getAuthentication(token);
		
		List<Object> resultado = null;
		
		try {
			resultado = conviteService.listarPorConvidado(emailAutenticado);
		} catch (BusinessException e) {
			System.err.println("BusinessException: " + e.getMessage());
		}
		
		return resultado;
	}

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/aceitar")
	public Response aceitarConvite(@HeaderParam("token") String token,@FormParam("id") int id,@FormParam("idConvite") int idConvite) {
		String emailAutenticado = TokenAuthenticationService.getAuthentication(token);
		
		try {
			conviteService.aceitar(emailAutenticado, id, idConvite);
			return Response.status(201).build();
		} catch (BusinessException e) {
			return Response.status(202).header("erro", e.getMessage()).build();
		}
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/recusar")
	public Response recusarConvite(@HeaderParam("token") String token,@FormParam("idConvite") int idConvite) {

		String emailAutenticado = TokenAuthenticationService.getAuthentication(token);
		
		try {
			conviteService.recusar(emailAutenticado, idConvite);
			return Response.status(201).build();
		} catch (BusinessException e) {
			return Response.status(202).header("erro", e.getMessage()).build();
		}
	}
	
}
