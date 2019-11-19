package br.ufrn.imd.meformando.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import br.ufrn.imd.meformando.dominio.Formando;
import br.ufrn.imd.meformando.dominio.Mensalidade;
import br.ufrn.imd.meformando.dominio.Pagamento;
import br.ufrn.imd.meformando.services.FormandoService;
import br.ufrn.imd.meformando.services.PagamentoService;
import br.ufrn.imd.meformando.util.TokenAuthenticationService;

@Stateless
@Path("/pagamento")
public class PagamentoController {

	@Inject
	private FormandoService formandoService;
	
	@Inject
	private PagamentoService pagamentoService;
	
	@GET
	@Produces("application/json; charset=UTF-8")
	@Path("/mensalidades")
	public List<Mensalidade> mensalidades(@HeaderParam("token") String token){
		String email = TokenAuthenticationService.getAuthentication(token);
		Formando formando = formandoService.getFormando(email);
		List<Mensalidade> mensalidades = pagamentoService.mensalidades(formando);
		return mensalidades;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/cadastrarMensalidade")
	public Response cadastrarMensalidade(@HeaderParam("token") String token, 
			@FormParam("mes") String mes, @FormParam("valor") double valor) {
		String email = TokenAuthenticationService.getAuthentication(token);
		Formando formando = formandoService.getFormando(email);
		
		if (!formando.isComissao()) {
			return Response.status(401).build();
		}
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
		
		try {
			Date mesData = formatter.parse(mes);
			pagamentoService.cadastrarMensalidade(formando.getTurma(), mesData, valor);
			return Response.status(201).build();
		} catch (ParseException e) {
			e.printStackTrace();
			return Response.status(400).build();
		}
	}
}
