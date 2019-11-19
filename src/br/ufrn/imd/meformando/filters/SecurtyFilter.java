package br.ufrn.imd.meformando.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.ufrn.imd.meformando.util.TokenAuthenticationService;


@WebFilter("/pages/*")
public class SecurtyFilter implements Filter{

	public void doFilter(ServletRequest request, ServletResponse response, 
			FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		String token = req.getHeader("token");

		String email = TokenAuthenticationService.getAuthentication(token);
		String path = req.getRequestURI().substring(req.getContextPath().length()).replaceAll("[/]+$", "");
		
		if (email == null && !path.equals("/usuario/logar") && !path.equals("/usuario/registrar"))
			//envia um erro de autoriza�ao
			res.sendError(401);
		else 
			chain.doFilter(request, response);
	}

}