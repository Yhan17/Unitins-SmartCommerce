package br.nunes.smartcommerce.model;

import java.time.LocalDate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class Produto extends Entity<Produto> {
	@NotBlank(message = "O nome deve ser informado.")
	private String name;

	private String description;

	private Brand brand;
	private Float price;
	@Min(1)
	private Integer stock;
	private String image;
	private LocalDate release_date;
	private LocalDate created_at;
	private LocalDate updated_at;

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;

	}

	public Brand getBrand() {
		return brand;
	}

	public Float getPrice() {
		return price;
	}

	public Integer getStock() {
		return stock;
	}

	public LocalDate getRelease_date() {
		return release_date;
	}

	public LocalDate getCreated_at() {
		return created_at;
	}

	public LocalDate getUpdated_at() {
		return updated_at;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public void setRelease_date(LocalDate release_date) {

		this.release_date = release_date;
	}

	public void setCreated_at(LocalDate created_at) {
		this.created_at = created_at;
	}

	public void setUpdated_at(LocalDate updated_at) {
		this.updated_at = updated_at;
	}

}
