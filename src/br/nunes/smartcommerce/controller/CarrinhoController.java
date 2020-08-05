package br.nunes.smartcommerce.controller;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.nunes.smartcommerce.application.Session;
import br.nunes.smartcommerce.application.Util;
import br.nunes.smartcommerce.dao.VendaDAO;
import br.nunes.smartcommerce.model.ItemVenda;
import br.nunes.smartcommerce.model.Produto;
import br.nunes.smartcommerce.model.User;
import br.nunes.smartcommerce.model.Venda;

@Named("carrinhoController")
@ViewScoped
public class CarrinhoController implements Serializable {

	private static final long serialVersionUID = -6966101618361824778L;

	private Venda venda;
	private User usuarioLogado;
	private List<ItemVenda> carrinho;

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



	public void remover(int produtoId) {
		carrinho = (ArrayList<ItemVenda>) Session.getInstance().getAttribute("carrinho");
		
		for (int i = 0; i < carrinho.size(); i++) {
			if(carrinho.get(i).getId() == produtoId) {
				carrinho.remove(i);
			}
		}
		
		
	}

	public void finalizar() {
		User usuario = (User) Session.getInstance().getAttribute("usuarioLogado");
		if (usuario == null) {
			Util.addWarningMessage("Eh preciso estar logado para realizar uma venda. Faca o Login!!");
			return;
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
			Util.addInfoMessage("Venda realizada com sucesso.");
			// limpando o carrinho
			Session.getInstance().setAttribute("carrinho", null);
		} else {
			Util.addErrorMessage("Erro ao finalizar a Venda.");
		}

	}

	public void setVenda(Venda venda) {

		this.venda = venda;
	}
}
