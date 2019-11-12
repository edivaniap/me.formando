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


import br.ufrn.imd.meformando.dominio.Convite;
import br.ufrn.imd.meformando.dominio.Formando;
import br.ufrn.imd.meformando.dominio.Turma;
import br.ufrn.imd.meformando.repositorios.ConviteRepositorio;
import br.ufrn.imd.meformando.repositorios.FormandoRepositorio;
import br.ufrn.imd.meformando.repositorios.TurmaRepositorio;
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
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces
	@Path("/logar")
	public Response logar(@FormParam("email") String email, 
			@FormParam("senha") String senha) {
		Formando formandoLogado = formandoRepositorio.findFormandoByEmail(email);
		if (formandoLogado != null && CryptService.verifyPasswords(senha, formandoLogado.getSenha())) {
			System.out.println("Deu certo!!!");
			System.out.println(formandoLogado.getEmail());
			System.out.println(CryptService.verifyPasswords(senha, formandoLogado.getSenha()));
			String token = TokenAuthenticationService.addAuthentication(email);
			return Response.status(201).header("token", token).build();
		}else {
			System.out.println("Deu errado!!!");
			System.out.println(formandoLogado.getEmail());
			System.out.println(CryptService.verifyPasswords(senha, formandoLogado.getSenha()));
			return Response.status(202).header("erro", "Senha ou Email Inválidos").build();
		}
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/registrar")
	public Response registrar(@FormParam("nome") String nome, @FormParam("cpf") String cpf, 
			@FormParam("email") String email, 
			@FormParam("senha") String senha) {
		Formando novoFormando = new Formando(nome, cpf, email, senha, false);
		if (formandoRepositorio.findFormandoByEmail(novoFormando.getEmail()) != null) {
			return Response.status(202).header("erro", "Usuário Existente").build();
		}else {
			formandoRepositorio.adicionar(novoFormando);
			return Response.status(201).build();
		}
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/aceitarConvite")
	public Response aceitarConvite(@HeaderParam("token") String token,@FormParam("id") int id) {
		String emailFormando = TokenAuthenticationService.getAuthentication(token);
		if (emailFormando == null) {
			//condição caso o token seja inválido
			return null;
		}else {
			Turma turma = turmaRepositorio.findTurmaById(id);
			if(turma != null) {
				Formando formando = formandoRepositorio.findFormandoByEmail(emailFormando);
				formando.setConfirmadoTurma(true);
				formando.setTurma(turma);
				formandoRepositorio.alterar(formando);
				return Response.status(201).build();
			}
			
			
			
			
			return null;
			
			
		}
	}
	
	
	
	@GET
	@Path("/confirmadoTurma")
	public boolean confirmadoTurma(@HeaderParam("token") String token){

		String emailFormando = TokenAuthenticationService.getAuthentication(token);
		if (emailFormando == null) {
			return false;
		}else {
			Formando formando = formandoRepositorio.findFormandoByEmail(emailFormando);
			return formando.isConfirmadoTurma();
		}
	}
	
	@GET
	@Path("/confirmadoComissao")
	public boolean confirmadoComissao(@HeaderParam("token") String token){

		String emailFormando = TokenAuthenticationService.getAuthentication(token);
		if (emailFormando == null) {
			return false;
		}else {
			Formando formando = formandoRepositorio.findFormandoByEmail(emailFormando);
			return formando.isComissao();
		}
	}
	
	@GET
	@Path("/convites")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces("application/json; charset=UTF-8")
	public List<Object> convites(@HeaderParam("token") String token) {
		String emailFormando = TokenAuthenticationService.getAuthentication(token);
		if (emailFormando == null) {
			System.out.println("Algo errado");
			return null;
		}else {
			Formando formando = formandoRepositorio.findFormandoByEmail(emailFormando);
			
			List<Convite> convites = conviteRepositorio.findConviteByFormando(formando.getId());
			List<Object> convitesDoFormando = new ArrayList<Object>();
			for(int i = 0 ; i < convites.size(); i++) {
				Convite convite = convites.get(i);
				Turma turma = turmaRepositorio.findTurmaById(convite.getIdDaTurma());
				convitesDoFormando.add(Arrays.asList(turma.getTitulo(),convite.getFormandoQueConvidou(),turma.getId()));
				
			}
			
			
			return convitesDoFormando;
			
			
		}
	}
	
	
	

}