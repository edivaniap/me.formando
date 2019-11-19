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

import br.ufrn.imd.meformando.dominio.Cerimonial;
import br.ufrn.imd.meformando.dominio.Evento;
import br.ufrn.imd.meformando.dominio.Formando;
import br.ufrn.imd.meformando.dominio.ProjetoArrecadacao;
import br.ufrn.imd.meformando.dominio.Turma;
import br.ufrn.imd.meformando.exceptions.BusinessException;
import br.ufrn.imd.meformando.services.FormandoService;
import br.ufrn.imd.meformando.services.ProjetoArrecadacaoService;
import br.ufrn.imd.meformando.services.TurmaService;
import br.ufrn.imd.meformando.util.TokenAuthenticationService;

@Stateless
@Path("/arrecadacao")
public class ProjetoArrecadacaoController {

	@EJB
	private ProjetoArrecadacaoService  projetoArrecadacaoService;
	
	@EJB
	private FormandoService formandoService;
	
	@EJB
	private TurmaService turmaService;
	
	/*USANDO O SERVICE*/
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/criar")
	public Response criar(@HeaderParam("token") String token,
			@FormParam("Titulo") String titulo, 
			@FormParam("Custo") double custo, 
			@FormParam("Ganho") double ganho,
			@FormParam("DateInicial") String dataInicial,
			@FormParam("DateFinal") String dataFinal) throws ParseException {
			
			String emailAutenticado = TokenAuthenticationService.getAuthentication(token);
			if (emailAutenticado == null) {
				return Response.status(202).build();
			}else {
				DateFormat formatter = new SimpleDateFormat("yy-MM-dd");
				Date dateInicial = (Date)formatter.parse(dataInicial);
				Date dateFinal = (Date)formatter.parse(dataFinal);
				
				Formando formandoAutenticado = formandoService.getFormando(emailAutenticado);
				
				ProjetoArrecadacao projetoArrecadacao = new ProjetoArrecadacao(titulo, custo, ganho, dateInicial, dateFinal);
				
				projetoArrecadacao.setTurma(formandoAutenticado.getTurma());
				
				
				try {
					projetoArrecadacaoService.adicionar(projetoArrecadacao);
				} catch (BusinessException e) {
					return Response.status(202).header("erro", e.getMessage()).build();
				}
				
				return Response.status(201).build();
			}
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/alterar")
	public Response alterar(@HeaderParam("token") String token,
			@HeaderParam("id") int id, 
			@FormParam("Titulo") String titulo, 
			@FormParam("Custo") double custo, 
			@FormParam("Ganho") double ganho,
			@FormParam("DateInicial") String dataInicial,
			@FormParam("DateFinal") String dataFinal)  throws ParseException {

			String emailAutenticado = TokenAuthenticationService.getAuthentication(token);
			if (emailAutenticado == null) {
			
				return Response.status(202).build();
			}else {
				
				DateFormat formatter = new SimpleDateFormat("yy-MM-dd");
				Date dateInicial = (Date)formatter.parse(dataInicial);
				Date dateFinal = (Date)formatter.parse(dataFinal);
				
				ProjetoArrecadacao projetoArrecadacao = projetoArrecadacaoService.getProjetoArrecadacaoId(id);
				projetoArrecadacao.setTitulo(titulo);
				projetoArrecadacao.setCusto(custo);
				projetoArrecadacao.setGanho(ganho);
				projetoArrecadacao.setDataInicial(dateInicial);
				projetoArrecadacao.setDataFinal(dateFinal);
				
							
				projetoArrecadacaoService.alterar(projetoArrecadacao);
			
				return Response.status(201).build();
			}

	}
	
	@GET
	@Path("/projetoArrecadacaoSelecionado")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces("application/json; charset=UTF-8")
	public List<Object> eventoSelecionado(@HeaderParam("token") String token,
			@HeaderParam("id") int id) {
		
		String emailAutenticado = TokenAuthenticationService.getAuthentication(token);
		if (emailAutenticado == null) {
			return null;
		}else {
			
			ProjetoArrecadacao projetoArrecadacao = projetoArrecadacaoService.getProjetoArrecadacaoId(id);
			
			if(projetoArrecadacao != null) {
				List<Object> turmaEnviado = new ArrayList<Object>();							
				turmaEnviado.add(Arrays.asList(projetoArrecadacao.getTitulo(), projetoArrecadacao.getCusto(),
						projetoArrecadacao.getGanho(), projetoArrecadacao.getDataInicial(), projetoArrecadacao.getDataFinal()));
				return turmaEnviado;
			}else {
				System.out.println("Deu ruim!!!");
				return null;
			}
			
			
		}
	}
	

}