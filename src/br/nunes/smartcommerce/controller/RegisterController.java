package br.nunes.smartcommerce.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import br.nunes.smartcommerce.application.Util;
import br.nunes.smartcommerce.dao.UserDAO;
import br.nunes.smartcommerce.model.User;

@Named
@RequestScoped
public class RegisterController extends Controller<User>{

	private static final long serialVersionUID = -2817596433066840999L;

	
	public RegisterController() {
		super(new UserDAO());
		// TODO Auto-generated constructor stub
	}

	public String registrar() {
		if (validarDados()) {
			if (dao.create(getEntity())) {
				limpar();
				Util.addInfoMessage("Inclusão realizada com sucesso.");
				return "login.xhtml?faces-redirect=true";
			} else {
				Util.addInfoMessage("Erro ao incluir no banco de dados.");
				return "";
			}
		}
		return "";
	}
	
	
	@Override
	public User getEntity() {
		if (entity == null)
			entity = new User();
		
		return entity;
	}
	
	@Override
	public boolean validarDados() {
		if (getEntity().getName().isBlank()) {
			Util.addErrorMessage("O campo nome deve ser informado.");
			return false;
		}
		
		// gerando o hash da senha
		String senha = Util.hashSHA256(getEntity().getPassword());
		getEntity().setPassword(senha);
		
		return true;
	}


}
