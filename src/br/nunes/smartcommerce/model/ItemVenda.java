package br.nunes.smartcommerce.model;

public class ItemVenda extends Entity<ItemVenda> {
	private Produto produto;
	private Float valor;
	private Venda venda;

	public Produto getProduto() {
		return produto;
	}

	public Float getValor() {
		return valor;
	}

	public Venda getVenda() {
		return venda;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public void setValor(Float valor) {
		this.valor = valor;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}


}
