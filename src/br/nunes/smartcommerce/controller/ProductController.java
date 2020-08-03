package br.nunes.smartcommerce.controller;

import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.nunes.smartcommerce.application.Session;
import br.nunes.smartcommerce.application.Util;
import br.nunes.smartcommerce.dao.ProdutoDAO;
import br.nunes.smartcommerce.model.Produto;
import br.nunes.smartcommerce.model.User;

@Named("productController")
@ViewScoped
public class ProductController extends Controller<Produto> {


	public ProductController() {
		super(new ProdutoDAO());
		// TODO Auto-generated constructor stub
	}

	private static final long serialVersionUID = -3227920903849017507L;
	
	private List<Produto> listaProduto;
	private User usuarioLogado;
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<Produto> getListaProduto() {
		if(listaProduto == null) {
			ProdutoDAO dao = new ProdutoDAO();
			listaProduto = dao.findAll();
		}
		return listaProduto;
	}
	
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
	
	public void deletarProduto(Produto produto) {

		if (dao.delete(produto.getId())) {
			Util.addInfoMessage("Remoção realizada com sucesso.");
		} else {
			Util.addInfoMessage("Erro ao remover no banco de dados.");
		}
	}
	
	public String addNew() {
		return "register_products.xhtml?faces-redirect=true";
	}
	
	public String edit(Produto produto) {
		ProdutoDAO dao = new ProdutoDAO();
		produto = dao.findById(produto.getId());

		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();

		flash.put("flashProduct", produto);
		return "/admin_page/edit_products.xhtml?faces-redirect=true";
	}
	

	

	@Override
	public Produto getEntity() {
		if (entity == null)
			entity = new Produto();
		return entity;
	}


}
