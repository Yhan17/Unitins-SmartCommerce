package br.nunes.smartcommerce.controller;

import java.io.Serializable;

import br.nunes.smartcommerce.model.Entity;
import br.nunes.smartcommerce.application.Util;
import br.nunes.smartcommerce.dao.DAO;

public abstract class Controller <T extends Entity<T>> implements Serializable{

	private static final long serialVersionUID = -2586247303572825216L;
	
	protected T entity = null;
	protected DAO<T> dao = null;
	
	public Controller(DAO<T> dao) {
		super();
		this.dao = dao;
	}
	
	public abstract T getEntity();
	
	public void setEntity(T entity) {
		this.entity = entity;
	}

	public void incluir() {
		if (validarDados()) {
			if (dao.create(getEntity())) {
				limpar();
				Util.addInfoMessage("Inclusão realizada com sucesso.");
			} else {
				Util.addInfoMessage("Erro ao incluir no banco de dados.");
			}
		}
		
	}

	public void alterar() {
		if (validarDados()) {		
			if (dao.update(getEntity())) {
				limpar();
				Util.addInfoMessage("Alteração realizada com sucesso.");
			} else {
				Util.addInfoMessage("Erro ao alterar no banco de dados.");
			}
		}
	}

	public void remover() {
		if (dao.delete(getEntity().getId())) {
			limpar();
			Util.addInfoMessage("Remoção realizada com sucesso.");
		} else {
			Util.addInfoMessage("Erro ao remover no banco de dados.");
		}
	}

	public void editar(T entity) {
		entity = dao.findById(entity.getId());
		setEntity(entity);
	}
	
	public boolean validarDados() {
		return true;
	}
	
	public void limpar() {
		entity = null;
	}
	
}
