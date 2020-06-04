package br.nunes.smartcommerce.controller;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.nunes.smartcommerce.application.Util;
import br.nunes.smartcommerce.dao.UserDAO;
import br.nunes.smartcommerce.model.User;

@Named("editUserController")
@ViewScoped
public class AdminEditUserController extends Controller<User>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1053544890511000312L;

	public AdminEditUserController() {
		super(new UserDAO());
		Flash flash = FacesContext.getCurrentInstance().
				getExternalContext().getFlash();
		flash.keep("flashUser");
		entity = (User) flash.get("flashUser");
	}
	
	@Override
	public User getEntity() {
		if (entity == null)
		entity = new User();
	return entity;
	}

	@Override
	public boolean validarDados() {
	
		if (getEntity().getPassword().isBlank()) {
			Util.addErrorMessage("O campo senha deve ser informado.");
			return false;
		}
		
		
		String senha = Util.hashSHA256(getEntity().getPassword());
		getEntity().setPassword(senha);
		
		return true;
	}
	
	public String alter() {
		if (validarDados()) {		
			if (dao.update(getEntity())) {
				try {
					limpar();
				   
					FacesContext.getCurrentInstance().addMessage(
					null, new FacesMessage("Alteração Realizada com Sucesso!"));
					
					
					FacesContext.getCurrentInstance()
						    .getExternalContext()
						    .getFlash().setKeepMessages(true);
				
					Thread.sleep(1000);
					return "/admin_page/home.xhtm?faces-redirect=true";
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				Util.addInfoMessage("Erro ao alterar no banco de dados.");
				return "";
			}
		}
		return "";
	}
	
}
