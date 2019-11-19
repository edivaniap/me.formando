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
import br.ufrn.imd.meformando.exceptions.BusinessException;
import br.ufrn.imd.meformando.services.ConviteService;
import br.ufrn.imd.meformando.util.TokenAuthenticationService;

@Stateless
@Path("/convites")
public class ConviteController {
	
	@EJB
	private ConviteService conviteService;
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/convidar")
	public Response convidar(@HeaderParam("token") String token,@FormParam("Email") String email) {
		if(token == null || token == "") {
			return Response.status(203).header("erro", "Token vazio ou null").build();
		}

		String emailAutenticado = TokenAuthenticationService.getAuthentication(token);
		if (emailAutenticado == null) {
			return Response.status(202).header("erro", "Nao foi possivel encontrar o Formando atraves do Token").build();
		}else {
			try {
				conviteService.convidar(email, emailAutenticado);
			} catch (BusinessException be) {
				return Response.status(202).header("erro", be.getMessage()).build();
			} catch (Exception e) {
				return Response.status(202).header("erro", e.getMessage()).build();
			}
		}
		return Response.status(201).build();
	}

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
	
	
	@GET
	@Path("/convidados")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces("application/json; charset=UTF-8")
	public List<Object> convidadosTurma(@HeaderParam("token") String token) {

		String emailAutenticado = TokenAuthenticationService.getAuthentication(token);
		
		List<Object> convidados = new ArrayList<Object>();
		
		if(emailAutenticado != null) {
			List<Convite> convites = conviteService.listarPorTurma(emailAutenticado);
			
			for (Convite c : convites) {
				convidados.add(Arrays.asList(c.getFormando().getNome(), c.getFormando().getEmail(), c.getStatus()));
			}
		}
		
		return convidados;
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
