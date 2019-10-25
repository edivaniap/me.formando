package br.ufrn.imd.meformando.filtros;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.ufrn.imd.meformando.dominio.Formando;

@WebFilter("/pages/*")
public class FiltroSeguranca {

	public void doFilter(ServletRequest request, ServletResponse response, 
			FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		Formando usuarioLogado = (Formando) req.getSession().getAttribute("usuarioLogado");
		
		if (usuarioLogado == null) 
			res.sendRedirect("/formando/index.jsf");
		else 
			chain.doFilter(request, response);
	}

}
