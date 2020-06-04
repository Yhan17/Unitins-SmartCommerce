package br.nunes.smartcommerce.controller;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named("indexController")
@RequestScoped
public class IndexController implements Serializable{

	private static final long serialVersionUID = -4231961551948569952L;

	//Metodo Teste
	public String login() {
		return "login.xhtml?faces-redirect=true";	
	}
	
	
}
