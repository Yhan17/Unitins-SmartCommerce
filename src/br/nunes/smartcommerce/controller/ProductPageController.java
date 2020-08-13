package br.nunes.smartcommerce.controller;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.nunes.smartcommerce.model.Produto;

@Named("productPageController")
@ViewScoped
public class ProductPageController implements Serializable {

	
	
	private static final long serialVersionUID = -1250469723088075807L;
	private Produto productPage;

	public ProductPageController() {
		Flash flash = FacesContext.getCurrentInstance().
				getExternalContext().getFlash();
		flash.keep("productRequired");
		productPage = (Produto) flash.get("productRequired");
	}
	
	public Produto getProductPage() {
		return productPage;
	}

	public void setProductPage(Produto productPage) {
		this.productPage = productPage;
	}

}
