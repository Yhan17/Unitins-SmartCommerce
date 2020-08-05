package br.nunes.smartcommerce.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.nunes.smartcommerce.application.Session;
import br.nunes.smartcommerce.dao.ProdutoDAO;
import br.nunes.smartcommerce.model.ItemVenda;
import br.nunes.smartcommerce.model.Produto;
import br.nunes.smartcommerce.model.User;

@Named("indexUserController")
@ViewScoped
public class IndexUserController implements Serializable {

	private static final long serialVersionUID = -4231961551948569952L;
	private List<Produto> listaProduto;
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

	public void adicionar(int idProduto) {
		ProdutoDAO dao = new ProdutoDAO();
		Produto produto = dao.findById(idProduto);

		if (Session.getInstance().getAttribute("carrinho") == null) {
			Session.getInstance().setAttribute("carrinho", new ArrayList<Produto>());
		}

		List<ItemVenda> carrinho = (ArrayList<ItemVenda>) Session.getInstance().getAttribute("carrinho");

		ItemVenda item = new ItemVenda();
		item.setProduto(produto);
		item.setValor(produto.getPrice());

		carrinho.add(item);

		Session.getInstance().setAttribute("carrinho", carrinho);

	}

	public int getCarrinho() {
		carrinho = (ArrayList<ItemVenda>) Session.getInstance().getAttribute("carrinho");
		if (carrinho == null)
			carrinho = new ArrayList<ItemVenda>();

		return carrinho.size();
	}

	public List<Produto> getListaProduto() {
		if (listaProduto == null) {
			ProdutoDAO dao = new ProdutoDAO();
			listaProduto = dao.findAll();
		}

		return listaProduto;
	}
}
