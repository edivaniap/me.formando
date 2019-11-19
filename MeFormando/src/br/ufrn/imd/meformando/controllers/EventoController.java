package br.ufrn.imd.meformando.controllers;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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

import br.ufrn.imd.meformando.dominio.Evento;
import br.ufrn.imd.meformando.dominio.Formando;
import br.ufrn.imd.meformando.exceptions.BusinessException;
import br.ufrn.imd.meformando.services.EventoService;
import br.ufrn.imd.meformando.services.FormandoService;
import br.ufrn.imd.meformando.util.TokenAuthenticationService;

@Stateless
@Path("/evento")
public class EventoController {
	
	@EJB
	private EventoService eventoService;
	
	@EJB
	private FormandoService formandoService;
	
	/*USANDO O SERVICE*/
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/criar")
	public Response criar(@HeaderParam("token") String token,@FormParam("Titulo") String titulo, @FormParam("Custo") double custo, 
			@FormParam("Date") String data,
			@FormParam("Descricao") String descricao ) throws ParseException {
			
			String emailAutenticado = TokenAuthenticationService.getAuthentication(token);
			if (emailAutenticado == null) {
				return Response.status(202).build();
			}else {
				DateFormat formatter = new SimpleDateFormat("yy-MM-dd");
				Date date = (Date)formatter.parse(data);
				
				Formando formandoAutenticado = formandoService.getFormando(emailAutenticado);
				
				Evento evento = new Evento(titulo,custo,date,descricao);
				
				evento.setCerimonial(formandoAutenticado.getTurma().getCerimonial());
				
				try {
					eventoService.adicionar(evento);
				} catch (BusinessException e) {
					return Response.status(202).header("erro", e.getMessage()).build();
				}
				
				return Response.status(201).build();
			}
	}

	/*USANDO O SERVICE*/
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/alterar")
	public Response alterar(@HeaderParam("token") String token,@HeaderParam("id") int id, @FormParam("Titulo") String titulo, 
			@FormParam("Custo") double custo, 
			@FormParam("Date") String data,
			@FormParam("Descricao") String descricao ) throws ParseException {

			String emailAutenticado = TokenAuthenticationService.getAuthentication(token);
			if (emailAutenticado == null) {
			
				return Response.status(202).build();
			}else {
				
				DateFormat formatter = new SimpleDateFormat("yy-MM-dd");
				Date date = (Date)formatter.parse(data);
				
				Evento evento = eventoService.getEventoId(id);
				evento.setData(date);
				evento.setTitulo(titulo);
				evento.setCusto(custo);
				evento.setDescricao(descricao);
				
				
				eventoService.alterar(evento);
				
				return Response.status(201).build();
			}

	}
	
	@GET
	@Path("/eventoSelecionado")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces("application/json; charset=UTF-8")
	public List<Object> eventoSelecionado(@HeaderParam("token") String token,@HeaderParam("id") int id) {
		String emailFormando = TokenAuthenticationService.getAuthentication(token);
		if (emailFormando == null) {
			return null;
		}else {
			
			Evento evento = eventoService.getEventoId(id);
			if(evento != null) {
				List<Object> cerimonialEnviado = new ArrayList<Object>();							
				cerimonialEnviado.add(Arrays.asList(evento.getTitulo(),evento.getDescricao(),evento.getData().toString(),evento.getCusto()));
				return cerimonialEnviado;
			}else {
				System.out.println("Deu ruim!!!");
				return null;
			}
			
			
		}
	}
	
}
