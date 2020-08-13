package br.nunes.smartcommerce.controller;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.nunes.smartcommerce.application.Session;
import br.nunes.smartcommerce.application.Util;
import br.nunes.smartcommerce.dao.UserDAO;
import br.nunes.smartcommerce.model.User;

@Named("paymentController")
@ViewScoped
public class PaymentController implements Serializable {

	private static final long serialVersionUID = 2672958279336247304L;
	User usuario = (User) Session.getInstance().getAttribute("usuarioLogado");

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public User getUsuario() {
		return usuario;
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}
	public String alterar(){
		UserDAO dao = new UserDAO();
		if(dao.addAttributes(getUsuario())) {
			FacesContext.getCurrentInstance().addMessage(
					null, new FacesMessage("Salvamento Realizado com sucesso"));
					
					
					FacesContext.getCurrentInstance()
						    .getExternalContext()
						    .getFlash().setKeepMessages(true);
			return "carrinho.xhtml?faces-redirect=true";
		}
		Util.addErrorMessage("Não foi possível Salvar no Banco");
		return "";
	}
}
