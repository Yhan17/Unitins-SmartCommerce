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


@WebFilter(filterName = "SecurityFilter", urlPatterns = {"*.xhtml"})
public class SecurityFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

// 	 	Para desabilitar o filter, descomente as duas proximas linhas e comente o restante		
		chain.doFilter(request, response);
		return;
		
//		HttpServletRequest servletRequest = (HttpServletRequest) request;
//		// imprime o endereco da pagina
//		String endereco = servletRequest.getRequestURI();
//		String admin_page_prefix = "admin_page";
////		System.out.println(endereco);
//		if (endereco.equals("/SmartCommerce/") || endereco.equals("/SmartCommerce/index.xhtml")||endereco.equals("/SmartCommerce/login.xhtml")||endereco.equals("/SmartCommerce/register.xhtml")) {
//			chain.doFilter(request, response);
//			return;
//		}
//		
//		// retorna a sessao corrente (false - para nao criar uma nova sessao)
//		HttpSession session = servletRequest.getSession(false);
//		
//		User usuario = null;
//		if (session != null)
//			usuario = (User) session.getAttribute("usuarioLogado");
//		
//		if ( usuario == null) {
//			((HttpServletResponse) response).sendRedirect("/SmartCommerce/login.xhtml");
//		}  else {
//			// nesse local podemos trabalhar as permissoes por pagina
//			
//			if(usuario.getIs_admin() == false && (endereco.equals("/SmartCommerce/index.xhtml") 
//			|| endereco.equals("/SmartCommerce/login.xhtml") 
//			|| endereco.equals("/SmartCommerce/register.xhtml")
//			|| endereco.equals("/SmartCommerce/index_user_loged.xhtml"))) {
//				chain.doFilter(request, response);
//				return;	
//			}else if(endereco.toLowerCase().contains(admin_page_prefix.toLowerCase()) && usuario.getIs_admin() == false) {
//				((HttpServletResponse) response).sendRedirect("/SmartCommerce/index_user_loged.xhtml");
//			}
//			
//			// segue o fluxo 
//			if(( endereco.equals("/SmartCommerce/index.xhtml") 
//			|| endereco.equals("/SmartCommerce/register.xhtml")
//			|| endereco.equals("/SmartCommerce/admin_page/home.xhtml")
//			|| endereco.equals("/SmartCommerce/edit_usuario.xhtml")
//			|| endereco.equals("/SmartCommerce/index_user_loged.xhtml")) && usuario.getIs_admin() == true) {
//				chain.doFilter(request, response);
//				return;
//			}	
//			
//
//			
//			chain.doFilter(request, response);
//			return;
//		}
		
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