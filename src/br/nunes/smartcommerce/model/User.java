package br.nunes.smartcommerce.model;

import java.time.LocalDate;

public class User extends Entity<User> {

	private String name;
	private String login;
	private String password;
	private Boolean is_admin;
	private LocalDate created_at;
	private LocalDate updated_at;
	private AtributoUsuario atributes = new AtributoUsuario(); 

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getIs_admin() {
		return is_admin;
	}

	public void setIs_admin(Boolean is_admin) {
		this.is_admin = is_admin;
	}

	public LocalDate getCreated_at() {
		return created_at;
	}

	public void setCreated_at(LocalDate created_at) {
		this.created_at = created_at;
	}

	public LocalDate getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(LocalDate updated_at) {
		this.updated_at = updated_at;
	}

	public AtributoUsuario getAtributes() {
		return atributes;
	}

	public void setAtributes(AtributoUsuario atributes) {
		this.atributes = atributes;
	}

}
