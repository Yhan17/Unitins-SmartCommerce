package br.nunes.smartcommerce.model;

import br.nunes.smartcommerce.model.Brand;

public enum Brand {
	APPLE(0,"Apple"),
	SAMSUNG(1,"Samsung"),
	MOTOROLA(2,"Motorola"),
	LG(3,"Lg"),
	XIAOMI(4,"Xiaomi"),
	HUAWEI(5,"Huawei"),
	SONY(6,"Sony");
	
	private int id;
	private String label;
	
	private Brand(int id, String label) {
		this.id = id;
		this.label = label;
	}

	public int getId() {
		return id;
	}

	public String getLabel() {
		return label;
	}

	public static Brand valueOf(int valor) {
		for (Brand brand : Brand.values()) {
			if (valor == brand.getId())
				return brand;
		} 
		return null;
	}
	
	
}
