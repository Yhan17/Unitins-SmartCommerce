package br.nunes.smartcommerce.controller;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.nunes.smartcommerce.application.Session;
import br.nunes.smartcommerce.application.Util;
import br.nunes.smartcommerce.dao.VendaDAO;
import br.nunes.smartcommerce.model.ItemVenda;
import br.nunes.smartcommerce.model.User;
import br.nunes.smartcommerce.model.Venda;

@Named("carrinhoController")
@ViewScoped
public class CarrinhoController implements Serializable {

	private static final long serialVersionUID = -6966101618361824778L;

	private Venda venda;
	private User usuarioLogado;


//	//Metodo Teste
//	public String login() {
//		return "login.xhtml?faces-redirect=true";	
//	}
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

	public Venda getVenda() {
		if (venda == null)
			venda = new Venda();

		// obtendo o carrinho da sessao
		List<ItemVenda> carrinho = (ArrayList<ItemVenda>) Session.getInstance().getAttribute("carrinho");

		// adicionando os itens do carrinho na venda
		if (carrinho == null)
			carrinho = new ArrayList<ItemVenda>();
		venda.setListaItemVenda(carrinho);

		return venda;
	}



	public void remover(int produto) {
		List<ItemVenda> carrinho = (ArrayList<ItemVenda>) Session.getInstance().getAttribute("carrinho");

//		List<ItemVenda> auxiliar = carrinho.parallelStream().filter(e -> e.getProduto().getId() == produto).collect(Collectors.toList());
//		
//		if(auxiliar.size() > 1) {
//			auxiliar = carrinho.parallelStream().distinct().collect(Collectors.toList());
//			Session.getInstance().setAttribute("carrinho", auxiliar);
//		}else {
//			carrinho.removeIf(x -> x.getProduto().getId() == produto);
//			Session.getInstance().setAttribute("carrinho", carrinho);
//
//		}
		
		
		//Solução do Rafael, funciona
		 int cont = 0;
	        for (ItemVenda itemVenda : carrinho) {
	            if (itemVenda.getProduto().getId() == produto) {
	                carrinho.remove(cont);
	                break;
	            }
	            cont++;
	        }
	    
		
		
		//Funciona, porém Remove os item duplicados o que 
//		carrinho.removeIf(x -> x.getProduto().getId() == produto);		
//		carrinho.forEach(s -> {
//			if(s.getProduto().getId() == produto) {
//				break;
//			}
//					
//		});
		Session.getInstance().setAttribute("carrinho", carrinho);
	}

	public String finalizar() {
		User usuario = (User) Session.getInstance().getAttribute("usuarioLogado");
		if(venda.getListaItemVenda().size() == 0) {
			Util.addWarningMessage("O carrinho está vazio não é possível finalizar a compra");
			return "";
		}
		if (usuario == null ) {
			Util.addWarningMessage("É preciso estar logado para realizar uma venda. Faca o Login!!");
			return "";
		}
		if(usuario.getAtributes().getNumCartao() == null) {
			return "payment.xhtml?faces-redirect=true";
		}
		// montar a venda
		Venda venda = new Venda();
		venda.setData(LocalDate.now());
		venda.setUsuario(usuario);
		List<ItemVenda> carrinho = (ArrayList<ItemVenda>) Session.getInstance().getAttribute("carrinho");
		venda.setListaItemVenda(carrinho);
		// salvar no banco
		VendaDAO dao = new VendaDAO();
		if (dao.create(venda)) {
			FacesContext.getCurrentInstance().addMessage(
			null, new FacesMessage("Venda Realizada com Sucesso"));
			
			
			FacesContext.getCurrentInstance()
				    .getExternalContext()
				    .getFlash().setKeepMessages(true);
			// limpando o carrinho
			Session.getInstance().setAttribute("carrinho", null);
			return "index_user_loged.xhtml?faces-redirect=true";

		} else {
			Util.addErrorMessage("Erro ao finalizar a Venda.");
			return "";

		}

	}
	
	public String navegar() {
		return "/admin_page/home.xhtml?faces-redirect=true";
	}

	public void setVenda(Venda venda) {

		this.venda = venda;
	}
}
