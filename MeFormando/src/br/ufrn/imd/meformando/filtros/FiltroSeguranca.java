package br.ufrn.imd.meformando.filtros;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.ufrn.imd.meformando.dominio.Formando;
import br.ufrn.imd.meformando.util.TokenAuthenticationService;

//alterar para as páginas que vamos usar
@WebFilter("/*")
public class FiltroSeguranca implements Filter{

	public void doFilter(ServletRequest request, ServletResponse response, 
			FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		String token = req.getHeader("token");
		String id = TokenAuthenticationService.getAuthentication(token);
		String path = req.getRequestURI().substring(req.getContextPath().length()).replaceAll("[/]+$", "");
		
		if (id == null && !path.equals("/usuario/logar") && !path.equals("/usuario/registrar")) {
			//envia um erro de autorizaçao
			res.sendError(401);
		}	
		else { 
			chain.doFilter(request, response);
		}
	}
}
