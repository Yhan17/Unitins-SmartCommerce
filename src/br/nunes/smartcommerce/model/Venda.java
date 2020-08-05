package br.nunes.smartcommerce.model;

import java.time.LocalDate;
import java.util.List;

public class Venda extends Entity<Venda> {
	private LocalDate data;
	private User usuario;
	private List<ItemVenda> listaItemVenda;
	
	private Float totalVenda = 0.0f;

	public LocalDate getData() {
		return data;
	}

	public User getUsuario() {
		return usuario;
	}

	public List<ItemVenda> getListaItemVenda() {
		return listaItemVenda;
	}

	public Float getTotalVenda() {
		return totalVenda;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}

	public void setListaItemVenda(List<ItemVenda> listaItemVenda) {
		this.listaItemVenda = listaItemVenda;
	}

	public void setTotalVenda(Float totalVenda) {
		this.totalVenda = totalVenda;
	}
	
	
}
