package br.nunes.smartcommerce.controller;

import java.io.Serializable;


import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.nunes.smartcommerce.application.Session;
import br.nunes.smartcommerce.model.User;

@Named("indexUserController")
@ViewScoped
public class IndexUserController implements Serializable {

	private static final long serialVersionUID = -4231961551948569952L;

	private User usuarioLogado;

//	//Metodo Teste
//	public String login() {
//		return "login.xhtml?faces-redirect=true";	
//	}
	public User getUsarioLogado() {
		if (usuarioLogado == null) // buscando o usuario da sessao
			usuarioLogado = (User) Session.getInstance().getAttribute("usuarioLogado");
		return usuarioLogado;
	}

	public String encerrarSessao() {
		// encerrando a sessao
		Session.getInstance().invalidateSession();
		return "/index.xhtml?faces-redirect=true";
	}
}
