package br.nunes.smartcommerce.model;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;

public class AtributoUsuario {
	@NotBlank
	private String endereco;
	@NotBlank
	private String telefone;
	@NotBlank
	private String numCartao;
	@NotBlank
	private String titular;
	@NotBlank
	private String numSeguranca;
	@NotBlank
	private String dataVencimento;

	public String getEndereco() {
		return endereco;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getNumCartao() {
		return numCartao;
	}

	public String getTitular() {
		return titular;
	}

	public String getNumSeguranca() {
		return numSeguranca;
	}

	public void setNumCartao(String numCartao) {
		this.numCartao = numCartao;
	}

	public void setTitular(String titular) {
		this.titular = titular;
	}

	public void setNumSeguranca(String numSerguranca) {
		this.numSeguranca = numSerguranca;
	}

	public String getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(String dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

}
