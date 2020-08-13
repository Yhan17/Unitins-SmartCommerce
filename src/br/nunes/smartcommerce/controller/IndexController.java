package br.nunes.smartcommerce.controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Named;

import br.nunes.smartcommerce.dao.ProdutoDAO;
import br.nunes.smartcommerce.model.Produto;

@Named("indexController")
@RequestScoped
public class IndexController implements Serializable{

	private List<Produto> listaProduto;
	private static final long serialVersionUID = -4231961551948569952L;

	//Metodo Teste
	public String login() {
		return "login.xhtml?faces-redirect=true";	
	}
	
	public List<Produto> getListaProduto() {
		if (listaProduto == null) {
			ProdutoDAO dao = new ProdutoDAO();
			listaProduto = dao.findAll();
		}

		return listaProduto;
	}
	
	public String productRedirect(Produto produto) {
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.put("productRequired", produto);
		return "product_page.xhtml?faces-redirect=true";
	}
	
}
