package br.nunes.smartcommerce.controller;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import br.nunes.smartcommerce.application.Util;
import br.nunes.smartcommerce.dao.UserDAO;
import br.nunes.smartcommerce.model.User;


@Named("loginController")
@RequestScoped
public class LoginController implements Serializable {

	private static final long serialVersionUID = 629096692865749606L;
	
	private User user = new User();
	
	
	public String logar() {
		UserDAO dao = new UserDAO();
		User user = dao.verificarLoginSenha(getUser().getLogin(), Util.hashSHA256(getUser().getPassword()));
		
		if (user != null)
			return "/admin_page/home.xhtml?faces-redirect=true";
		Util.addErrorMessage("Login ou Senha inválido.");
		return "";
	}

	public void limpar() {
		user = null;
	}

	public User getUser() {
		if (user == null)
			user = new User();
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}
	
	

}
