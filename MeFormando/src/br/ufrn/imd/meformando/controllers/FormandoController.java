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
import br.ufrn.imd.meformando.repositories.FormandoRepository;
import br.ufrn.imd.meformando.repositories.TurmaRepository;
import br.ufrn.imd.meformando.services.FormandoService;
import br.ufrn.imd.meformando.util.CryptService;
import br.ufrn.imd.meformando.util.TokenAuthenticationService;

@Stateless
@Path("/usuario")
public class FormandoController {
	
	@Inject
	private FormandoRepository formandoRepository;
	
	@Inject 
	private TurmaRepository turmaRepository;
	
	@Inject 
	private ConviteRepository conviteRepository;
	
	@EJB
	private FormandoService formandoService;
	
	
	/* USANDO O SERVICE */
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
		} catch (BusinessException e) {
			return Response.status(202).header("erro", e.getMessage()).build();
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
			//condicao caso o token seja invalido
			return null;
		}else {
			Turma turma = turmaRepository.findTurmaById(id);
			if(turma != null) {
				Formando formando = formandoRepository.findFormandoByEmail(emailFormando);
				formando.setConfirmadoTurma(true);
				formando.setTurma(turma);
				formandoRepository.alterar(formando);
				
				Convite convite = conviteRepository.findConviteById(idConvite);
				conviteRepository.remover(convite);
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
			//condicao caso o token seja invalido
			return null;
		}else {
			Turma turma = turmaRepository.findTurmaById(id);
			if(turma != null) {
				Convite convite = conviteRepository.findConviteById(idConvite);
				conviteRepository.remover(convite);
				return Response.status(201).build();
			}
			
			return null;
		}
	}
	
	
	/* USANDO O SERVICE */
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
	
	
	/* USANDO O SERVICE */
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
	
	/* USANDO O SERVICE */
	// em andamento. e esta mt confuso, precisa revisar classe convite
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
			
			List<Convite> convites = conviteRepository.findConviteByFormando(formandoLogado.getId());
			List<Object> convitesDoFormando = new ArrayList<Object>();
			for(int i = 0 ; i < convites.size(); i++) {
				Convite convite = convites.get(i);
				Turma turma = turmaRepository.findTurmaById(convite.getIdDaTurma());
				convitesDoFormando.add(Arrays.asList(turma.getTitulo(),convite.getFormandoQueConvidou(),turma.getId(),convite.getId()));
				
			}
			return convitesDoFormando;
		}
	}
}