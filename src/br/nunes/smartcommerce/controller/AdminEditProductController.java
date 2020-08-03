package br.nunes.smartcommerce.controller;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.nunes.smartcommerce.application.Session;
import br.nunes.smartcommerce.application.Util;
import br.nunes.smartcommerce.dao.ProdutoDAO;
import br.nunes.smartcommerce.model.Brand;
import br.nunes.smartcommerce.model.Produto;
import br.nunes.smartcommerce.model.User;

@Named("editProductController")
@ViewScoped
public class AdminEditProductController extends Controller<Produto> {


	private static final long serialVersionUID = 460310369802881573L;
	
	private User usuarioLogado;
	
	public AdminEditProductController() {
		super(new ProdutoDAO());
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.keep("flashProduto");
		entity = (Produto) flash.get("flashProduct");	
	}

	@Override
	public Produto getEntity() {
		if(entity == null)
			entity = new Produto();
		return entity;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String encerrarSessao() {
		// encerrando a sessao
		Session.getInstance().invalidateSession();
		return "/index.xhtml?faces-redirect=true";
	}
	
	
	public User getUsuarioLogado() {
		if (usuarioLogado == null) // buscando o usuario da sessao
			usuarioLogado = (User) Session.getInstance().getAttribute("usuarioLogado");
		return usuarioLogado;
	}

	public void setUsuarioLogado(User usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}
	
	public String update() {
		if (validarDados()) {
			if (dao.update(getEntity())) {
				try {
					limpar();

					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage("Alteração Realizada com Sucesso!"));

					FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

					Thread.sleep(1000);
					return "/admin_page/product_home.xhtml?faces-redirect=true";
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				Util.addInfoMessage("Erro ao Alteração no banco de dados.");
				return "";
			}
		}
		return "";
	}

	public Brand[] getListaTipoProduto() {
		return Brand.values();
	}




	
	@Override
	public boolean validarDados() {
		if (getEntity().getName().isBlank()) {
			Util.addErrorMessage("O campo nome deve ser informado.");
			return false;
		}
	return true;
	}
	
	
}
