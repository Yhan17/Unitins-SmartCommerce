package br.nunes.smartcommerce.controller;

import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.nunes.smartcommerce.application.Session;
import br.nunes.smartcommerce.application.Util;
import br.nunes.smartcommerce.dao.UserDAO;
import br.nunes.smartcommerce.model.User;

@Named("adminHomeController")
@ViewScoped
public class AdminHomeController extends Controller<User> {

	public AdminHomeController() {
		super(new UserDAO());

	}

	private static final long serialVersionUID = -7937112228220362392L;

	private List<User> usuario;
	private User usuarioLogado;

	public List<User> getUsuario() {
		if (usuario == null) {
			UserDAO dao = new UserDAO();
			usuario = dao.findAll();
		}

		return usuario;
		
	}

	@Override
	public User getEntity() {
		if (entity == null)
			entity = new User();

		return entity;
	}

	public String edit(User user) {
		UserDAO dao = new UserDAO();
		user = dao.findById(user.getId());

		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();

		flash.put("flashUser", user);
		return "/admin_page/edit_usuario.xhtml?faces-redirect=true";
	}

	public void deletarUsuario(User user) {

		if (dao.delete(user.getId())) {
			Util.addInfoMessage("Remoção realizada com sucesso.");
		} else {
			Util.addInfoMessage("Erro ao remover no banco de dados.");
		}
	}

	public User getUsarioLogado() {
		if (usuarioLogado == null) // buscando o usuario da sessao
			usuarioLogado = (User) Session.getInstance().getAttribute("usuarioLogado");	
		return usuarioLogado;
	}

	public void setUsarioLogado(User usarioLogado) {
		this.usuarioLogado = usarioLogado;
	}
	
	public String encerrarSessao() {
		// encerrando a sessao
		Session.getInstance().invalidateSession();
		return "/index.xhtml?faces-redirect=true";
	}
	
}
