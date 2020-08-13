package br.nunes.smartcommerce.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.nunes.smartcommerce.application.Session;
import br.nunes.smartcommerce.dao.VendaDAO;
import br.nunes.smartcommerce.model.User;
import br.nunes.smartcommerce.model.Venda;

@Named("historicoVendaController")
@ViewScoped
public class HistoricoVendaController implements Serializable {

	private static final long serialVersionUID = -7884238350928835736L;

	private List<Venda> listaVenda = null;

	public List<Venda> getListaVenda() {
		if (listaVenda == null) {
			VendaDAO dao = new VendaDAO();
			User usuario = (User) Session.getInstance().getAttribute("usuarioLogado");
			listaVenda = dao.findByUsuario(usuario.getId());
			if (listaVenda == null)
				listaVenda = new ArrayList<Venda>();
		}
		return listaVenda;
	}

	public String detalhes(Venda venda) {
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.put("detalheVenda", venda);

		return "detalhesvenda.xhtml?faces-redirect=true";
	}

}
