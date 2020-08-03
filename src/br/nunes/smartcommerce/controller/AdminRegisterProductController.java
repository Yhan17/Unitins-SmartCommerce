package br.nunes.smartcommerce.controller;


import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.nunes.smartcommerce.application.Session;
import br.nunes.smartcommerce.application.Util;
import br.nunes.smartcommerce.dao.ProdutoDAO;
import br.nunes.smartcommerce.model.Brand;
import br.nunes.smartcommerce.model.Produto;
import br.nunes.smartcommerce.model.User;

@Named("registerProductController")
@ViewScoped
public class AdminRegisterProductController extends Controller<Produto> {

	private List<Produto> listaProduto;
	private static final long serialVersionUID = -7912559731952920320L;
	
	private User usuarioLogado;

	public AdminRegisterProductController() {
		super(new ProdutoDAO());
		// TODO Auto-generated constructor stub
	}

	public List<Produto> getListaProduto() {
		if (listaProduto == null) {
			ProdutoDAO dao = new ProdutoDAO();
			listaProduto = dao.findAll();
		}

		return listaProduto;
	}
	
	public String encerrarSessao() {
		// encerrando a sessao
		Session.getInstance().invalidateSession();
		return "/index.xhtml?faces-redirect=true";
	}

	public User getUsarioLogado() {
		if (usuarioLogado == null) // buscando o usuario da sessao
			usuarioLogado = (User) Session.getInstance().getAttribute("usuarioLogado");	
		return usuarioLogado;
	}
	
	public String include() {
		if (validarDados()) {		
			if (dao.create(getEntity())) {
				try {
					limpar();
				   
					FacesContext.getCurrentInstance().addMessage(
					null, new FacesMessage("Inserção Realizada com Sucesso!"));
					
					
					FacesContext.getCurrentInstance()
						    .getExternalContext()
						    .getFlash().setKeepMessages(true);
				
					Thread.sleep(1000);
					return "/admin_page/product_home.xhtml?faces-redirect=true";
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				Util.addInfoMessage("Erro ao Inserir no banco de dados.");
				return "";
			}
		}
		return "";
	}
	
	public String update() {
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
	public Produto getEntity() {

		if (entity == null) {
			entity = new Produto();
		}
		return entity;
		
	}
	@Override
	public boolean validarDados() {
		if (getEntity().getName().isBlank() ) {
			Util.addErrorMessage("O campo nome deve ser informado.");
			return false;
		}
				
		return true;
	}
}
