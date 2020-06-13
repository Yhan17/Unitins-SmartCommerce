package br.nunes.smartcommerce.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.nunes.smartcommerce.model.User;


@WebFilter(filterName = "SecurityFilter", urlPatterns = {"/faces/*"})
public class SecurityFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

// 	 	Para desabilitar o filter, descomente as duas proximas linhas e comente o restante		
//		chain.doFilter(request, response);
//		return;
		
		HttpServletRequest servletRequest = (HttpServletRequest) request;
		// imprime o endereco da pagina
		String endereco = servletRequest.getRequestURI();
		System.out.println(endereco);
		if (endereco.equals("/SmartCommerce/faces/index.xhtml")||endereco.equals("/SmartCommerce/faces/login.xhtml")||endereco.equals("/SmartCommerce/faces/register.xhtml")) {
			chain.doFilter(request, response);
			return;
		}
		
		// retorna a sessao corrente (false - para nao criar uma nova sessao)
		HttpSession session = servletRequest.getSession(false);
		
		User usuario = null;
		if (session != null)
			usuario = (User) session.getAttribute("usuarioLogado");
		
		if (usuario == null) {
			((HttpServletResponse) response).sendRedirect("/SmartCommerce/faces/login.xhtml");
		}  else {
			// nesse local podemos trabalhar as permissoes por pagina

			// segue o fluxo 
			chain.doFilter(request, response);
			return;
		}
		
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("SecurityFilter Iniciado.");
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	

}